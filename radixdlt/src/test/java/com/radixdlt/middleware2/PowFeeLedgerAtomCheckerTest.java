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

package com.radixdlt.middleware2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import com.radixdlt.consensus.Sha256Hasher;
import com.radixdlt.constraintmachine.CMInstruction;
import com.radixdlt.constraintmachine.CMMicroInstruction;
import com.radixdlt.crypto.HashUtils;
import com.radixdlt.crypto.Hasher;
import com.radixdlt.identifiers.AID;
import com.radixdlt.universe.Universe;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PowFeeLedgerAtomCheckerTest {
	private PowFeeLedgerAtomChecker checker;
	private Universe universe;
	private PowFeeComputer powFeeComputer;
	private HashCode target;
	private Hasher hasher;

	@Before
	public void setUp() {
		this.hasher = Sha256Hasher.withDefaultSerialization();
		this.universe = mock(Universe.class);
		when(universe.getGenesis()).thenReturn(Collections.emptyList());
		this.powFeeComputer = mock(PowFeeComputer.class);
		this.target = HashCode.fromBytes(new byte[2]);
		this.checker = new PowFeeLedgerAtomChecker(
			universe,
			powFeeComputer,
			target,
			hasher
		);
	}

	@Test
	public void when_validating_atom_with_particles__result_has_no_error() {
		when(universe.getGenesis()).thenReturn(Collections.emptyList());
		ClientAtom ledgerAtom = mock(ClientAtom.class);
		CMInstruction cmInstruction = new CMInstruction(
			ImmutableList.of(mock(CMMicroInstruction.class)), HashUtils.random256(), ImmutableMap.of()
		);
		when(ledgerAtom.getAID()).thenReturn(mock(AID.class));
		when(ledgerAtom.getCMInstruction()).thenReturn(cmInstruction);
		when(ledgerAtom.getMetaData()).thenReturn(
			ImmutableMap.of(
				"timestamp", "0",
				"powNonce", "0"
			)
		);

		HashCode powSpent = HashCode.fromBytes(new byte[1]);
		when(powFeeComputer.computePowSpent(eq(ledgerAtom), eq(0L))).thenReturn(powSpent);
		assertThat(checker.check(ledgerAtom).isSuccess()).isTrue();
	}

	@Test
	public void when_validating_atom_without_particles__result_has_error() {
		ClientAtom ledgerAtom = mock(ClientAtom.class);
		CMInstruction cmInstruction = new CMInstruction(
			ImmutableList.of(), HashUtils.random256(), ImmutableMap.of()
		);
		when(ledgerAtom.getAID()).thenReturn(mock(AID.class));
		when(ledgerAtom.getCMInstruction()).thenReturn(cmInstruction);
		when(ledgerAtom.getMetaData()).thenReturn(ImmutableMap.of("timestamp", "0"));

		assertThat(checker.check(ledgerAtom).getErrorMessage())
			.contains("instructions");
	}

	@Test
	public void when_validating_atom_without_metadata__result_has_error() {
		LedgerAtom ledgerAtom = mock(ClientAtom.class);
		CMInstruction cmInstruction = new CMInstruction(
			ImmutableList.of(mock(CMMicroInstruction.class)), HashUtils.random256(), ImmutableMap.of()
		);
		when(ledgerAtom.getAID()).thenReturn(mock(AID.class));
		when(ledgerAtom.getCMInstruction()).thenReturn(cmInstruction);
		when(ledgerAtom.getMetaData()).thenReturn(ImmutableMap.of());

		assertThat(checker.check(ledgerAtom).getErrorMessage())
			.contains("metadata does not contain");
	}
}