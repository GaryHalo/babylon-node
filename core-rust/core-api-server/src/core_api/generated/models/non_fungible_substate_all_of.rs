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
pub struct NonFungibleSubstateAllOf {
    /// The hex-encoded bytes of its non-fungible id
    #[serde(rename = "non_fungible_id_hex")]
    pub non_fungible_id_hex: String,
    #[serde(rename = "is_deleted")]
    pub is_deleted: bool,
    #[serde(rename = "non_fungible_data", skip_serializing_if = "Option::is_none")]
    pub non_fungible_data: Option<Box<crate::core_api::generated::models::NonFungibleData>>,
}

impl NonFungibleSubstateAllOf {
    pub fn new(non_fungible_id_hex: String, is_deleted: bool) -> NonFungibleSubstateAllOf {
        NonFungibleSubstateAllOf {
            non_fungible_id_hex,
            is_deleted,
            non_fungible_data: None,
        }
    }
}


