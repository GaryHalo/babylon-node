/*
 * Babylon Core API
 *
 * This API is exposed by the Babylon Radix node to give clients access to the Radix Engine, Mempool and State in the node. It is intended for use by node-runners on a private network, and is not intended to be exposed publicly. Heavy load may impact the node's function.  If you require queries against historical ledger state, you may also wish to consider using the [Gateway API](https://betanet-gateway.redoc.ly/). 
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 * Generated by: https://openapi-generator.tech
 */




#[derive(Clone, Debug, PartialEq, Default, serde::Serialize, serde::Deserialize)]
pub struct ValidatorSetSubstateAllOf {
    #[serde(rename = "validator_set")]
    pub validator_set: Vec<crate::core_api::generated::models::EcdsaSecp256k1PublicKey>,
    /// An integer between `0` and `10^10`, marking the epoch the validator set is a part of
    #[serde(rename = "epoch")]
    pub epoch: i64,
}

impl ValidatorSetSubstateAllOf {
    pub fn new(validator_set: Vec<crate::core_api::generated::models::EcdsaSecp256k1PublicKey>, epoch: i64) -> ValidatorSetSubstateAllOf {
        ValidatorSetSubstateAllOf {
            validator_set,
            epoch,
        }
    }
}


