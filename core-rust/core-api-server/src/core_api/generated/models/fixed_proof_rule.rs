/*
 * Babylon Core API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 * Generated by: https://openapi-generator.tech
 */



#[derive(Clone, Debug, PartialEq, serde::Serialize, serde::Deserialize)]
#[serde(tag = "type")]
pub enum FixedProofRule {
    #[serde(rename="AllOf")]
    AllOfFixedProofRule {
        #[serde(rename = "resources")]
        resources: Vec<crate::core_api::generated::models::FixedResourceDescriptor>,
    },
    #[serde(rename="AmountOf")]
    AmountOfFixedProofRule {
        /// The string-encoded decimal representing the amount of resource required to pass the proof rule. A decimal is formed of some signed integer `m` of attos (`10^(-18)`) units, where `-2^(256 - 1) <= m < 2^(256 - 1)`. 
        #[serde(rename = "amount")]
        amount: String,
        #[serde(rename = "resource")]
        resource: Box<crate::core_api::generated::models::FixedResourceDescriptor>,
    },
    #[serde(rename="AnyOf")]
    AnyOfFixedProofRule {
        #[serde(rename = "resources")]
        resources: Vec<crate::core_api::generated::models::FixedResourceDescriptor>,
    },
    #[serde(rename="CountOf")]
    CountOfFixedProofRule {
        #[serde(rename = "count")]
        count: i32,
        #[serde(rename = "resources")]
        resources: Vec<crate::core_api::generated::models::FixedResourceDescriptor>,
    },
    #[serde(rename="Require")]
    RequireFixedProofRule {
        #[serde(rename = "resource")]
        resource: Box<crate::core_api::generated::models::FixedResourceDescriptor>,
    },
}




