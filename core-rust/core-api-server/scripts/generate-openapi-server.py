import urllib.request, logging, subprocess, os, shutil

# Inspired by https://github.com/radixdlt/radixdlt-python-clients
# Requires python 3+ and various packages above

logger = logging.getLogger()
logging.basicConfig(format='%(asctime)s [%(levelname)s]: %(message)s', level=logging.INFO)

CORE_API_SPEC_LOCATION = '../core-api-spec.yaml'
CORE_API_GENERATED_DESTINATION = '../src/core_api/generated/'

OPENAPI_GENERATION_FOLDER='.'
OPENAPI_TEMP_GENERATION_FOLDER='./temp'
OPENAPI_GENERATOR_FIXED_VERSION_JAR=os.path.join(OPENAPI_GENERATION_FOLDER, 'openapi-generator-cli-6.0.1.jar')
OPENAPI_GENERATOR_FIXED_VERSION_DOWNLOAD_URL='https://search.maven.org/remotecontent?filepath=org/openapitools/openapi-generator-cli/6.0.1/openapi-generator-cli-6.0.1.jar'

def safe_os_remove(path, silent = False):
    try:
        shutil.rmtree(path) if os.path.isdir(path) else os.remove(path)
    except Exception as e:
        if not silent: logger.warning(e)

def replace_in_file(filename, target, replacement):
    with open(filename, 'r') as file:
        file_contents = file.read()
    file_contents = file_contents.replace(target, replacement)
    with open(filename, 'w') as file:
        file.write(str(file_contents))

def create_file(filename, file_contents):
    with open(filename, 'w') as file:
        file.write(str(file_contents))

def copy_file(source, dest):
    shutil.copyfile(source, dest)

def run(command, cwd = '.', should_log = False):
    if (should_log): logging.debug('Running cmd: %s' % command)
    response = subprocess.run(' '.join(command), cwd=cwd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stderr = response.stderr.decode('utf-8')
    if response.returncode != 0: raise Exception(stderr)
    stdout = response.stdout.decode('utf-8')
    if (should_log): logging.debug('Response: %s', stdout)
    return stdout

def generate_models(spec_file, tmp_client_folder, out_location):
    safe_os_remove(out_location, True)
    # See https://openapi-generator.tech/docs/generators/rust/
    run(['java', '-jar', OPENAPI_GENERATOR_FIXED_VERSION_JAR, 'generate',
         '-g', 'rust',
         '-i', spec_file,
         '-o', tmp_client_folder,
         # Prepare for the release 6.0.2, which will include these properties:
         # From this PR: https://github.com/OpenAPITools/openapi-generator/commit/a1892b163688d205ee8a44051b59aba3928ec5fc
         # This will enable us removing the i32/i64 changes below
         '--additional-properties=preferUnsignedInt=true,bestFitInt=true'
         ], should_log=False)

    logging.info("Successfully generated.")

    rust_code_root = os.path.join(tmp_client_folder, 'src')
    rust_models = os.path.join(rust_code_root, 'models')
    out_models = os.path.join(out_location, 'models')

    os.makedirs(os.path.join(out_location))
    shutil.copytree(rust_models, out_models)
    create_file(os.path.join(out_location, 'mod.rs'), "pub mod models;")

    file_names = [file_name for file_name in os.listdir(out_models) if os.path.isfile(os.path.join(out_models, file_name))]
    file_names_to_not_convert_to_unsigned = set(["resource_change.rs"])
    for file_name in file_names:
        file_path = os.path.join(out_models, file_name)
        replace_in_file(file_path, 'crate::', 'crate::core_api::generated::')
        replace_in_file(file_path, ', Serialize, Deserialize', ', serde::Serialize, serde::Deserialize')
        # This can be removed when we update to 6.0.2 when it's out - see above
        if file_name not in file_names_to_not_convert_to_unsigned:
            replace_in_file(file_path, 'i8', 'u8')
            replace_in_file(file_path, 'i16', 'u16')
            replace_in_file(file_path, 'i32', 'u32')
            replace_in_file(file_path, 'i64', 'u64')

    # Fix bugs in generation:
    substate_enum_file = os.path.join(out_models, "substate.rs")
    up_substate_file = os.path.join(out_models, "up_substate.rs")
    vault_substate_file = os.path.join(out_models, "vault_substate.rs")
    vault_substate_all_of_file = os.path.join(out_models, "vault_substate_all_of.rs")
    
    # Fix bug that discriminator tags are stripped and lower cased
    replace_in_file(substate_enum_file, 'tag = "substatetype"', 'tag = "substate_type"')

    # Fix bug that enums don't implement Default... So replace their references with Options
    replace_in_file(up_substate_file, 'Box<crate::core_api::generated::models::Substate>,', 'Option<crate::core_api::generated::models::Substate>, // Using Option permits Default trait; Will always be Some in normal use')
    replace_in_file(up_substate_file, 'substate_data: Box::new(substate_data)', 'substate_data: Option::Some(substate_data)')
    
    replace_in_file(vault_substate_file, 'Box<crate::core_api::generated::models::ResourceAmount>,', 'Option<crate::core_api::generated::models::ResourceAmount>, // Using Option permits Default trait; Will always be Some in normal use')
    replace_in_file(vault_substate_all_of_file, 'Box<crate::core_api::generated::models::ResourceAmount>,', 'Option<crate::core_api::generated::models::ResourceAmount>, // Using Option permits Default trait; Will always be Some in normal use')
    replace_in_file(vault_substate_file, 'resource_amount: Box::new(resource_amount)', 'resource_amount: Option::Some(resource_amount)')
    replace_in_file(vault_substate_all_of_file, 'resource_amount: Box::new(resource_amount)', 'resource_amount: Option::Some(resource_amount)')

    logging.info("Successfully fixed up.")

if __name__ == "__main__":
    logger.info('Will generate models from the API specifications')

    # Set working directory to be the same directory as this script
    os.chdir(os.path.dirname(os.path.abspath(__file__)))

    # check & download the openapi-generator.jar
    if not os.path.exists(OPENAPI_GENERATOR_FIXED_VERSION_JAR):
        logger.info('%s does not exist' % OPENAPI_GENERATOR_FIXED_VERSION_JAR)
        logger.info('Will download it from %s...' % OPENAPI_GENERATOR_FIXED_VERSION_DOWNLOAD_URL)
        urllib.request.urlretrieve(OPENAPI_GENERATOR_FIXED_VERSION_DOWNLOAD_URL, OPENAPI_GENERATOR_FIXED_VERSION_JAR)
        logger.info('Testing the openapi-generator...')
        logger.info(run(['ls %s' % OPENAPI_GENERATION_FOLDER]))
        run(['java', '-jar', OPENAPI_GENERATOR_FIXED_VERSION_JAR, 'author'], should_log=False)
        logger.info('All good.')

    safe_os_remove(OPENAPI_TEMP_GENERATION_FOLDER, silent=True)
    os.makedirs(OPENAPI_TEMP_GENERATION_FOLDER)

    # download & fix the spec files
    core_api_spec_temp_filename = os.path.join(OPENAPI_TEMP_GENERATION_FOLDER, 'core_api_spec.yaml')
    copy_file(CORE_API_SPEC_LOCATION, core_api_spec_temp_filename)
    replace_in_file(core_api_spec_temp_filename, 'openapi: 3.1.0', 'openapi: 3.0.0')
    logging.info('Loaded Core API Spec from {}'.format(os.path.abspath(CORE_API_SPEC_LOCATION)))

    logging.info('Generating code from specs...')

    generate_models(core_api_spec_temp_filename, os.path.join(OPENAPI_TEMP_GENERATION_FOLDER, "core-api"), CORE_API_GENERATED_DESTINATION)

    logging.info("Code has been created.")

    # clean up
    # safe_os_remove(OPENAPI_TEMP_GENERATION_FOLDER, silent=True)
