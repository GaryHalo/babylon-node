/*
 * Babylon Core API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 * Generated by: https://openapi-generator.tech
 */




#[derive(Clone, Debug, PartialEq, Default, serde::Serialize, serde::Deserialize)]
pub struct CommittedTransaction {
    /// The resultant state version after the txn has been committed. A decimal 64-bit unsigned integer.
    #[serde(rename = "state_version")]
    pub state_version: u64,
    #[serde(rename = "notarized_transaction")]
    pub notarized_transaction: Box<crate::core_api::generated::models::NotarizedTransaction>,
    #[serde(rename = "receipt")]
    pub receipt: Box<crate::core_api::generated::models::TransactionReceipt>,
}

impl CommittedTransaction {
    pub fn new(state_version: u64, notarized_transaction: crate::core_api::generated::models::NotarizedTransaction, receipt: crate::core_api::generated::models::TransactionReceipt) -> CommittedTransaction {
        CommittedTransaction {
            state_version,
            notarized_transaction: Box::new(notarized_transaction),
            receipt: Box::new(receipt),
        }
    }
}


