/*
 * (C) Copyright 2020 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.radixdlt.statecomputer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.radixdlt.DefaultSerialization;
import com.radixdlt.consensus.Command;
import com.radixdlt.consensus.Vertex;
import com.radixdlt.consensus.VertexMetadata;
import com.radixdlt.consensus.bft.BFTValidatorSet;
import com.radixdlt.consensus.bft.View;
import com.radixdlt.engine.RadixEngine;
import com.radixdlt.engine.RadixEngineException;
import com.radixdlt.identifiers.EUID;
import com.radixdlt.middleware2.ClientAtom;
import com.radixdlt.serialization.SerializationException;
import com.radixdlt.syncer.CommittedAtom;
import com.radixdlt.middleware2.LedgerAtom;
import com.radixdlt.middleware2.store.CommittedAtomsStore;
import com.radixdlt.syncer.CommittedCommands;
import com.radixdlt.syncer.SyncExecutor.StateComputer;
import com.radixdlt.syncer.SyncExecutor.CommittedCommand;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Wraps the Radix Engine and emits messages based on success or failure
 */
public final class RadixEngineStateComputer implements StateComputer {
	private final CommittedAtomsStore committedAtomsStore;
	private final RadixEngine<LedgerAtom> radixEngine;
	private final Function<Long, BFTValidatorSet> validatorSetMapping;
	private final View epochChangeView;

	private final Object lock = new Object();
	private final LinkedList<CommittedAtom> unstoredCommittedAtoms = new LinkedList<>();

	public RadixEngineStateComputer(
		RadixEngine<LedgerAtom> radixEngine,
		Function<Long, BFTValidatorSet> validatorSetMapping,
		View epochChangeView,
		CommittedAtomsStore committedAtomsStore
	) {
		if (epochChangeView.isGenesis()) {
			throw new IllegalArgumentException("Epoch change view must not be genesis.");
		}

		this.radixEngine = Objects.requireNonNull(radixEngine);
		this.validatorSetMapping = validatorSetMapping;
		this.epochChangeView = epochChangeView;
		this.committedAtomsStore = Objects.requireNonNull(committedAtomsStore);
	}

	// TODO Move this to a different class class when unstored committed atoms is fixed
	public List<CommittedAtom> getCommittedAtoms(long stateVersion, int batchSize) {
		// TODO: This may still return an empty list as we still count state versions for atoms which
		// TODO: never make it into the radix engine due to state errors. This is because we only check
		// TODO: validity on commit rather than on proposal/prepare.
		// TODO: remove 100 hardcode limit
		List<CommittedAtom> storedCommittedAtoms = committedAtomsStore.getCommittedAtoms(stateVersion, batchSize);

		// TODO: Remove
		final List<CommittedAtom> copy;
		synchronized (lock) {
			copy = new ArrayList<>(unstoredCommittedAtoms);
		}

		return Streams.concat(
			storedCommittedAtoms.stream(),
			copy.stream().filter(a -> a.getVertexMetadata().getStateVersion() > stateVersion)
		)
			.sorted(Comparator.comparingLong(a -> a.getVertexMetadata().getStateVersion()))
			.collect(ImmutableList.toImmutableList());
	}

	@Override
	public CommittedCommand commit(Command command, VertexMetadata vertexMetadata) {
		if (command != null) {
			final ClientAtom clientAtom;
			try {
				clientAtom = DefaultSerialization.getInstance().fromDson(command.getPayload(), ClientAtom.class);
			} catch (SerializationException e) {
				// TODO: Fix this
				throw new IllegalStateException("Dead.");
			}

			CommittedAtom committedAtom = new CommittedAtom(clientAtom, vertexMetadata);

			try {
				// TODO: execute list of commands instead
				this.radixEngine.checkAndStore(committedAtom);

				// TODO: cleanup and move this logic to a better spot
				final ImmutableSet<EUID> indicies = committedAtomsStore.getIndicies(committedAtom);
				return CommittedCommands.success(command, vertexMetadata, indicies);
			} catch (RadixEngineException e) {
				// TODO: Don't check for state computer errors for now so that we don't
				// TODO: have to deal with failing leader proposals
				// TODO: Reinstate this when ProposalGenerator + Mempool can guarantee correct proposals

				// TODO: move VIRTUAL_STATE_CONFLICT to static check
				this.unstoredCommittedAtoms.add(committedAtom);

				return CommittedCommands.error(command, vertexMetadata, e);
			}

		} else if (vertexMetadata.isEndOfEpoch()) {
			// TODO: HACK
			// TODO: Remove and move epoch change logic into RadixEngine
			this.unstoredCommittedAtoms.add(new CommittedAtom(null, vertexMetadata));

			return CommittedCommands.success(null, vertexMetadata, ImmutableSet.of());
		} else {
			// TODO: HACK
			// TODO: Refactor to remove such illegal states
			throw new IllegalStateException("Should never get here " + command);
		}
	}

	@Override
	public Optional<BFTValidatorSet> prepare(Vertex vertex) {
		if (vertex.getView().compareTo(epochChangeView) >= 0) {
			return Optional.of(validatorSetMapping.apply(vertex.getEpoch() + 1));
		} else {
			return Optional.empty();
		}
	}
}
