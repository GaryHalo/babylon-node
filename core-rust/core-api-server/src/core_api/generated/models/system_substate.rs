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
pub struct SystemSubstate {
    #[serde(rename = "entity_type")]
    pub entity_type: crate::core_api::generated::models::EntityType,
    #[serde(rename = "substate_type")]
    pub substate_type: crate::core_api::generated::models::SubstateType,
    #[serde(rename = "epoch")]
    pub epoch: u64,
}

impl SystemSubstate {
    pub fn new(entity_type: crate::core_api::generated::models::EntityType, substate_type: crate::core_api::generated::models::SubstateType, epoch: u64) -> SystemSubstate {
        SystemSubstate {
            entity_type,
            substate_type,
            epoch,
        }
    }
}


