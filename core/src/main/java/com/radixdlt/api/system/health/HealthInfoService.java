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

package com.radixdlt.api.system.health;

import static com.radixdlt.api.system.health.HealthInfoService.ValueHolder.Type.ABSOLUTE;
import static com.radixdlt.api.system.health.NodeStatus.*;

import com.google.inject.Inject;
import com.radixdlt.environment.EventProcessor;
import com.radixdlt.environment.ScheduledEventDispatcher;
import com.radixdlt.monitoring.Metrics;

public class HealthInfoService {
  private static final long THRESHOLD = 3; // Maximum difference between ledger and target
  private static final long DEFAULT_COLLECTING_INTERVAL = 1000L; // 1 second
  private static final long STATUS_AVERAGING_FACTOR =
      3L; // averaging time in multiples of collecting interval

  private final Metrics metrics;
  private final ScheduledEventDispatcher<ScheduledStatsCollecting> scheduledStatsCollecting;
  private final ValueHolder ledgerStateVersion;
  private final ValueHolder syncTargetStateVersion;

  @Inject
  public HealthInfoService(
      Metrics metrics,
      ScheduledEventDispatcher<ScheduledStatsCollecting> scheduledStatsCollecting) {
    this.scheduledStatsCollecting = scheduledStatsCollecting;
    this.metrics = metrics;
    this.ledgerStateVersion = new ValueHolder(STATUS_AVERAGING_FACTOR, ABSOLUTE);
    this.syncTargetStateVersion = new ValueHolder(STATUS_AVERAGING_FACTOR, ABSOLUTE);
    scheduledStatsCollecting.dispatch(
        ScheduledStatsCollecting.create(), DEFAULT_COLLECTING_INTERVAL);
  }

  public NodeStatus nodeStatus() {
    if (this.ledgerStateVersion.lastValue() == 0) {
      // Initial status, consensus not started yet
      return BOOTING;
    }

    if (this.ledgerStateVersion.isGrowing()) {
      // Ledger state version is increasing, so we're completely synced up or catching up
      return ledgerIsCloseToTarget() ? UP : SYNCING;
    }

    // Ledger is not growing, either node stall or whole network is down or not reachable
    return this.syncTargetStateVersion.isGrowing() ? STALLED : OUT_OF_SYNC;
  }

  public EventProcessor<ScheduledStatsCollecting> updateStats() {
    return flush -> {
      collectStats();
      scheduledStatsCollecting.dispatch(
          ScheduledStatsCollecting.create(), DEFAULT_COLLECTING_INTERVAL);
    };
  }

  private void collectStats() {
    ledgerStateVersion.update((long) metrics.ledger().stateVersion().get());
    syncTargetStateVersion.update((long) metrics.sync().targetStateVersion().get());
  }

  private boolean ledgerIsCloseToTarget() {
    return (syncTargetStateVersion.lastValue() - ledgerStateVersion.lastValue()) < THRESHOLD;
  }

  static class ValueHolder {
    private final MovingAverage calculator;
    private final MovingAverage deltaCalculator;
    private final Type type;
    private long lastValue;

    public enum Type {
      ABSOLUTE,
      INCREMENTAL
    }

    private ValueHolder(long averagingFactor, Type type) {
      calculator = MovingAverage.create(averagingFactor);
      deltaCalculator = MovingAverage.create(averagingFactor);
      this.type = type;
    }

    public ValueHolder update(long newValue) {
      var lastDelta = newValue - lastValue;

      lastValue = newValue;
      deltaCalculator.update(lastDelta);

      if (type == ABSOLUTE) {
        calculator.update(newValue);
      } else {
        calculator.update(lastDelta);
      }

      return this;
    }

    public long lastValue() {
      return lastValue;
    }

    public boolean isGrowing() {
      return deltaCalculator.asDouble() > 0.1;
    }
  }
}
