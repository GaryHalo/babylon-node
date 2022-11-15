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
pub struct RequireFixedProofRule {
    #[serde(rename = "type")]
    pub _type: crate::core_api::generated::models::FixedProofRuleType,
    #[serde(rename = "resource")]
    pub resource: Option<crate::core_api::generated::models::FixedResourceDescriptor>, // Using Option permits Default trait; Will always be Some in normal use
}

impl RequireFixedProofRule {
    pub fn new(_type: crate::core_api::generated::models::FixedProofRuleType, resource: crate::core_api::generated::models::FixedResourceDescriptor) -> RequireFixedProofRule {
        RequireFixedProofRule {
            _type,
            resource: Option::Some(resource),
        }
    }
}


