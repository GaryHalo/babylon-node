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
pub struct NonFungibleResourceManagerSubstateAllOf {
    #[serde(rename = "non_fungible_id_type")]
    pub non_fungible_id_type: crate::core_api::generated::models::NonFungibleIdType,
    /// The string-encoded decimal representing the total supply of this resource. A decimal is formed of some signed integer `m` of attos (`10^(-18)`) units, where `-2^(256 - 1) <= m < 2^(256 - 1)`. 
    #[serde(rename = "total_supply")]
    pub total_supply: String,
    #[serde(rename = "non_fungible_data_table")]
    pub non_fungible_data_table: Box<crate::core_api::generated::models::EntityReference>,
    #[serde(rename = "non_fungible_data_type_index")]
    pub non_fungible_data_type_index: Box<crate::core_api::generated::models::LocalTypeIndex>,
    /// The field names of the NF Metadata which are mutable. 
    #[serde(rename = "non_fungible_data_mutable_fields")]
    pub non_fungible_data_mutable_fields: Vec<String>,
}

impl NonFungibleResourceManagerSubstateAllOf {
    pub fn new(non_fungible_id_type: crate::core_api::generated::models::NonFungibleIdType, total_supply: String, non_fungible_data_table: crate::core_api::generated::models::EntityReference, non_fungible_data_type_index: crate::core_api::generated::models::LocalTypeIndex, non_fungible_data_mutable_fields: Vec<String>) -> NonFungibleResourceManagerSubstateAllOf {
        NonFungibleResourceManagerSubstateAllOf {
            non_fungible_id_type,
            total_supply,
            non_fungible_data_table: Box::new(non_fungible_data_table),
            non_fungible_data_type_index: Box::new(non_fungible_data_type_index),
            non_fungible_data_mutable_fields,
        }
    }
}


