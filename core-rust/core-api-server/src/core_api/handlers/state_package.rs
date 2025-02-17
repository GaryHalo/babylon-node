use crate::core_api::*;
use radix_engine::system::node_substates::PersistedSubstate;
use radix_engine::types::{PackageOffset, SubstateOffset};
use radix_engine_interface::api::types::{AccessRulesOffset, NodeModuleId, RENodeId};

use state_manager::jni::state_manager::ActualStateManager;

pub(crate) async fn handle_state_package(
    state: State<CoreApiState>,
    request: Json<models::StatePackageRequest>,
) -> Result<Json<models::StatePackageResponse>, ResponseError<()>> {
    core_api_read_handler(state, request, handle_state_package_internal)
}

fn handle_state_package_internal(
    state_manager: &ActualStateManager,
    request: models::StatePackageRequest,
) -> Result<models::StatePackageResponse, ResponseError<()>> {
    assert_matching_network(&request.network, &state_manager.network)?;
    let extraction_context = ExtractionContext::new(&state_manager.network);

    let package_address = extract_package_address(&extraction_context, &request.package_address)
        .map_err(|err| err.into_response_error("package_address"))?;

    let package_info = {
        let substate_offset = SubstateOffset::Package(PackageOffset::Info);
        let loaded_substate = read_mandatory_substate(
            state_manager,
            RENodeId::GlobalObject(package_address.into()),
            NodeModuleId::SELF,
            &substate_offset,
        )?;
        let PersistedSubstate::PackageInfo(substate) = loaded_substate else {
            return Err(wrong_substate_type(substate_offset));
        };
        substate
    };
    let package_royalty = {
        let substate_offset = SubstateOffset::Package(PackageOffset::Royalty);
        let loaded_substate = read_mandatory_substate(
            state_manager,
            RENodeId::GlobalObject(package_address.into()),
            NodeModuleId::SELF,
            &substate_offset,
        )?;
        let PersistedSubstate::PackageRoyalty(substate) = loaded_substate else {
            return Err(wrong_substate_type(substate_offset));
        };
        substate
    };
    let package_access_rules = {
        let substate_offset = SubstateOffset::AccessRules(AccessRulesOffset::AccessRules);
        let loaded_substate = read_mandatory_substate(
            state_manager,
            RENodeId::GlobalObject(package_address.into()),
            NodeModuleId::AccessRules,
            &substate_offset,
        )?;
        let PersistedSubstate::MethodAccessRules(substate) = loaded_substate else {
            return Err(wrong_substate_type(substate_offset));
        };
        substate
    };

    let mapping_context = MappingContext::new(&state_manager.network);

    Ok(models::StatePackageResponse {
        info: Some(to_api_package_info_substate(
            &mapping_context,
            &package_info,
        )?),
        royalty: Some(to_api_package_royalty_substate(
            &mapping_context,
            &package_royalty,
        )?),
        access_rules: Some(to_api_access_rules_chain_substate(
            &mapping_context,
            &package_access_rules,
        )?),
    })
}
