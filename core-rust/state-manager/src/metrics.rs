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

use prometheus::core::*;
use prometheus::*;

// TODO: Can move these into separate modules if we wish to, as long as they all register to the same registry
pub struct StateManagerMetrics {
    pub ledger_state_version: IntGauge,
    pub ledger_transactions_committed: IntCounter,
    pub ledger_last_update_epoch_second: Gauge,
    pub mempool_current_transactions: IntGauge,
    pub mempool_submission_added: IntCounterVec,
    pub mempool_submission_rejected: IntCounterVec,
}

impl StateManagerMetrics {
    pub fn new() -> Self {
        Self {
            ledger_transactions_committed: IntCounter::with_opts(opts(
                "ledger_transactions_committed_total",
                "Count of transactions committed to the ledger.",
            ))
            .unwrap(),
            ledger_last_update_epoch_second: Gauge::with_opts(opts(
                "ledger_last_update_epoch_second",
                "Last timestamp at which the ledger was updated.",
            ))
            .unwrap(),
            ledger_state_version: IntGauge::with_opts(opts(
                "ledger_state_version",
                "Version of the ledger state.",
            ))
            .unwrap(),
            mempool_current_transactions: IntGauge::with_opts(opts(
                "mempool_current_transactions",
                "Number of transactions in progress in the mempool.",
            ))
            .unwrap(),
            mempool_submission_added: IntCounterVec::new(
                opts(
                    "mempool_submission_added_total",
                    "Count of submissions added to the mempool.",
                ),
                &["source"],
            )
            .unwrap(),
            mempool_submission_rejected: IntCounterVec::new(
                opts(
                    "mempool_submission_rejected_total",
                    "Count of the submissions rejected by the mempool.",
                ),
                &["source", "rejection_reason"],
            )
            .unwrap(),
        }
    }

    pub fn register_with(&self, registry: &Registry) {
        let metrics: Vec<Box<dyn Collector>> = vec![
            Box::new(self.ledger_state_version.clone()),
            Box::new(self.ledger_transactions_committed.clone()),
            Box::new(self.ledger_last_update_epoch_second.clone()),
            Box::new(self.mempool_current_transactions.clone()),
            Box::new(self.mempool_submission_added.clone()),
            Box::new(self.mempool_submission_rejected.clone()),
        ];

        for metric in metrics.into_iter() {
            registry.register(metric).unwrap();
        }
    }
}

// Appease clippy
impl Default for StateManagerMetrics {
    fn default() -> Self {
        Self::new()
    }
}

fn opts(name: &str, help: &str) -> Opts {
    Opts::new(format!("rn_{name}"), help)
}

// TODO - capture the metric types on a generic wrapper around the GenericCounter, and ensure the provided labels match the types, like in Java.
pub trait TakesMetricLabels {
    type Metric;

    fn with_label(&self, label1: impl MetricLabel) -> Self::Metric;
    fn with_two_labels(&self, label1: impl MetricLabel, label2: impl MetricLabel) -> Self::Metric;
    fn with_three_labels(
        &self,
        label1: impl MetricLabel,
        label2: impl MetricLabel,
        label3: impl MetricLabel,
    ) -> Self::Metric;
}

impl<T: MetricVecBuilder> TakesMetricLabels for MetricVec<T> {
    type Metric = <T as MetricVecBuilder>::M;

    fn with_label(&self, label1: impl MetricLabel) -> Self::Metric {
        self.with_label_values(&[label1.prometheus_label_name().as_ref()])
    }

    fn with_two_labels(&self, label1: impl MetricLabel, label2: impl MetricLabel) -> Self::Metric {
        self.with_label_values(&[
            label1.prometheus_label_name().as_ref(),
            label2.prometheus_label_name().as_ref(),
        ])
    }

    fn with_three_labels(
        &self,
        label1: impl MetricLabel,
        label2: impl MetricLabel,
        label3: impl MetricLabel,
    ) -> Self::Metric {
        self.with_label_values(&[
            label1.prometheus_label_name().as_ref(),
            label2.prometheus_label_name().as_ref(),
            label3.prometheus_label_name().as_ref(),
        ])
    }
}

/// Typically applied to enums or Errors where we wish to derive a label name.
/// Note the label name returned should be in a fixed, small-ish sized-set, to prevent
/// issues with tracking too many metrics, or combinatorial explosion of metrics with different labels.
pub trait MetricLabel {
    /// Typically &str, but could also be String if it's dynamic (as long as it's in a fixed small set)
    type StringReturnType: AsRef<str>;

    /// Returns the string label associated with this enum value.
    fn prometheus_label_name(&self) -> Self::StringReturnType;
}

/// We implement it for &T so that you can pass references in to function parameters taking `impl MetricLabel`
impl<T: MetricLabel> MetricLabel for &T {
    type StringReturnType = <T as MetricLabel>::StringReturnType;

    fn prometheus_label_name(&self) -> Self::StringReturnType {
        T::prometheus_label_name(self)
    }
}
