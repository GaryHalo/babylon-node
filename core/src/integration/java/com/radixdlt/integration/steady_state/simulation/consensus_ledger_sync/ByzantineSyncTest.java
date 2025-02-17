/* Copyright 2021 Radix Publishing Ltd incorporated in Jersey (Channel Islands).
 *
 * Licensed under the Radix License, Version 1.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * radixfoundation.org/licenses/LICENSE-v1
 *
 * The Licensor hereby grants permission for the Canonical version of the Work to be
 * published, distributed and used under or by reference to the Licensor’s trademark
 * Radix ® and use of any unregistered trade names, logos or get-up.
 *
 * The Licensor provides the Work (and each Contributor provides its Contributions) on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT,
 * MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Whilst the Work is capable of being deployed, used and adopted (instantiated) to create
 * a distributed ledger it is your responsibility to test and validate the code, together
 * with all logic and performance of that code under all foreseeable scenarios.
 *
 * The Licensor does not make or purport to make and hereby excludes liability for all
 * and any representation, warranty or undertaking in any form whatsoever, whether express
 * or implied, to any entity or person, including any representation, warranty or
 * undertaking, as to the functionality security use, value or other characteristics of
 * any distributed ledger nor in respect the functioning or value of any tokens which may
 * be created stored or transferred using the Work. The Licensor does not warrant that the
 * Work or any use of the Work complies with any law or regulation in any territory where
 * it may be implemented or used or that it will be appropriate for any specific purpose.
 *
 * Neither the licensor nor any current or former employees, officers, directors, partners,
 * trustees, representatives, agents, advisors, contractors, or volunteers of the Licensor
 * shall be liable for any direct or indirect, special, incidental, consequential or other
 * losses of any kind, in tort, contract or otherwise (including but not limited to loss
 * of revenue, income or profits, or loss of use or data, or loss of reputation, or loss
 * of any economic or other opportunity of whatsoever nature or howsoever arising), arising
 * out of or in connection with (without limitation of any use, misuse, of any ledger system
 * or use made or its functionality or any performance or operation of any code or protocol
 * caused by bugs or programming or logic errors or otherwise);
 *
 * A. any offer, purchase, holding, use, sale, exchange or transmission of any
 * cryptographic keys, tokens or assets created, exchanged, stored or arising from any
 * interaction with the Work;
 *
 * B. any failure in a transmission or loss of any token or assets keys or other digital
 * artefacts due to errors in transmission;
 *
 * C. bugs, hacks, logic errors or faults in the Work or any communication;
 *
 * D. system software or apparatus including but not limited to losses caused by errors
 * in holding or transmitting tokens by any third-party;
 *
 * E. breaches or failure of security including hacker attacks, loss or disclosure of
 * password, loss of private key, unauthorised use or misuse of such passwords or keys;
 *
 * F. any losses including loss of anticipated savings or other benefits resulting from
 * use of the Work or any changes to the Work (however implemented).
 *
 * You are solely responsible for; testing, validating and evaluation of all operation
 * logic, functionality, security and appropriateness of using the Work for any commercial
 * or non-commercial purpose and for any reproduction or redistribution by You of the
 * Work. You assume all risks associated with Your use of the Work and the exercise of
 * permissions under this License.
 */

package com.radixdlt.integration.steady_state.simulation.consensus_ledger_sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.multibindings.ProvidesIntoSet;
import com.radixdlt.environment.EventProcessorOnDispatch;
import com.radixdlt.harness.simulation.*;
import com.radixdlt.harness.simulation.SimulationTest.Builder;
import com.radixdlt.harness.simulation.monitors.consensus.ConsensusMonitors;
import com.radixdlt.harness.simulation.monitors.ledger.LedgerMonitors;
import com.radixdlt.ledger.LedgerUpdate;
import com.radixdlt.modules.FunctionalRadixNodeModule.ConsensusConfig;
import com.radixdlt.sync.SometimesByzantineCommittedReader;
import com.radixdlt.sync.SyncRelayConfig;
import com.radixdlt.sync.TransactionsAndProofReader;
import java.util.LongSummaryStatistics;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/** Any number/sort of byzantine sync modules should never be able to cause a safety failure. */
public class ByzantineSyncTest {
  private static final Logger logger = LogManager.getLogger();
  private final Builder bftTestBuilder;

  public ByzantineSyncTest() {
    this.bftTestBuilder =
        SimulationTest.builder()
            .numPhysicalNodes(5)
            .networkModules(
                NetworkOrdering.inOrder(),
                NetworkLatencies.fixed(10),
                NetworkDroppers.fNodesAllReceivedProposalsDropped())
            .addOverrideModuleToAllInitialNodes(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    bind(TransactionsAndProofReader.class)
                        .to(SometimesByzantineCommittedReader.class)
                        .in(Scopes.SINGLETON);
                    bind(SometimesByzantineCommittedReader.class).in(Scopes.SINGLETON);
                  }

                  @ProvidesIntoSet
                  @Singleton
                  private EventProcessorOnDispatch<?> eventProcessor(
                      SometimesByzantineCommittedReader reader) {
                    return new EventProcessorOnDispatch<>(
                        LedgerUpdate.class, reader.ledgerUpdateEventProcessor());
                  }
                })
            .ledgerAndSync(ConsensusConfig.of(3000), SyncRelayConfig.of(200L, 10, 1000L), 5);
  }

  @Test
  public void given_a_sometimes_byzantine_sync_layer__sanity_tests_should_pass() {
    SimulationTest simulationTest =
        bftTestBuilder
            .addTestModules(
                ConsensusMonitors.safety(),
                ConsensusMonitors.liveness(10, TimeUnit.SECONDS),
                ConsensusMonitors.directParents(),
                LedgerMonitors.consensusToLedger(),
                LedgerMonitors.ordered())
            .build();
    final var runningTest = simulationTest.run();
    final var results = runningTest.awaitCompletion();
    assertThat(results).allSatisfy((name, err) -> assertThat(err).isEmpty());

    LongSummaryStatistics statistics =
        runningTest.getNetwork().getMetrics().values().stream()
            .mapToLong(s -> (long) s.sync().validResponsesReceived().get())
            .summaryStatistics();

    logger.info("{}", statistics);
    assertThat(statistics.getSum()).isGreaterThan(0L);
  }

  @Test
  public void
      given_a_sometimes_byzantine_sync_layer_with_incorrect_accumulator_verifier__sanity_tests_should_not_pass() {
    SimulationTest simulationTest =
        bftTestBuilder
            .addTestModules(LedgerMonitors.ordered())
            .addOverrideModuleToAllInitialNodes(
                new IncorrectAlwaysAcceptingAccumulatorVerifierModule())
            .build();
    final var runningTest = simulationTest.run();
    final var checkResults = runningTest.awaitCompletion();

    LongSummaryStatistics statistics =
        runningTest.getNetwork().getMetrics().values().stream()
            .mapToLong(s -> (long) s.sync().validResponsesReceived().get())
            .summaryStatistics();

    logger.info("{}", statistics);
    assertThat(checkResults)
        .hasEntrySatisfying(Monitor.LEDGER_IN_ORDER, error -> assertThat(error).isPresent());
  }
}
