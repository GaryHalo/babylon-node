/*
 * Babylon Core API - RCnet V1
 *
 * This API is exposed by the Babylon Radix node to give clients access to the Radix Engine, Mempool and State in the node.  It is intended for use by node-runners on a private network, and is not intended to be exposed publicly. Very heavy load may impact the node's function.  This API exposes queries against the node's current state (see `/lts/state/` or `/state/`), and streams of transaction history (under `/lts/stream/` or `/stream`).  If you require queries against snapshots of historical ledger state, you may also wish to consider using the [Gateway API](https://docs-babylon.radixdlt.com/).  ## Integration and forward compatibility guarantees  This version of the Core API belongs to the first release candidate of the Radix Babylon network (\"RCnet-V1\").  Integrators (such as exchanges) are recommended to use the `/lts/` endpoints - they have been designed to be clear and simple for integrators wishing to create and monitor transactions involving fungible transfers to/from accounts.  All endpoints under `/lts/` are guaranteed to be forward compatible to Babylon mainnet launch (and beyond). We may add new fields, but existing fields will not be changed. Assuming the integrating code uses a permissive JSON parser which ignores unknown fields, any additions will not affect existing code.  We give no guarantees that other endpoints will not change before Babylon mainnet launch, although changes are expected to be minimal. 
 *
 * The version of the OpenAPI document: 0.3.0
 * 
 * Generated by: https://openapi-generator.tech
 */




#[derive(Clone, Debug, PartialEq, Default, serde::Serialize, serde::Deserialize)]
pub struct CommittedTransaction {
    /// An integer between `1` and `10^13`, giving the resultant state version after the transaction has been committed
    #[serde(rename = "state_version")]
    pub state_version: i64,
    /// The hex-encoded transaction accumulator hash. This hash captures the order of all transactions on ledger. This hash is `ACC_{N+1} = Blake2b-256(CONCAT(ACC_N, LEDGER_HASH_{N}))`, starting with `ACC_0 = 000..000` the pre-genesis accumulator. 
    #[serde(rename = "accumulator_hash")]
    pub accumulator_hash: String,
    #[serde(rename = "ledger_transaction")]
    pub ledger_transaction: Option<crate::core_api::generated::models::LedgerTransaction>, // Using Option permits Default trait; Will always be Some in normal use
    #[serde(rename = "receipt")]
    pub receipt: Box<crate::core_api::generated::models::TransactionReceipt>,
}

impl CommittedTransaction {
    pub fn new(state_version: i64, accumulator_hash: String, ledger_transaction: crate::core_api::generated::models::LedgerTransaction, receipt: crate::core_api::generated::models::TransactionReceipt) -> CommittedTransaction {
        CommittedTransaction {
            state_version,
            accumulator_hash,
            ledger_transaction: Option::Some(ledger_transaction),
            receipt: Box::new(receipt),
        }
    }
}


