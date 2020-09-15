import static com.radixdlt.test.AssertionChecks.*;

import com.radixdlt.test.AssertionChecks;
import com.radixdlt.test.Docker;
import com.radixdlt.test.DockerNetwork;
import com.radixdlt.test.RemoteBFTNetworkBridge;
import com.radixdlt.test.RemoteBFTTest;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestName;
import utils.CmdHelper;
import utils.Generic;

public class OutOfSynchronyBoundsTest {

	private static final Logger logger = LogManager.getLogger();

	@Rule
	public TestName name = new TestName();


	@Test
	@Category(Docker.class)
	public void given_4_correct_bfts_in_latent_docker_network__when_one_instance_is_down__then_all_instances_should_get_same_commits_and_progress_should_be_made() {


		String name = Generic.extractTestName(this.name.getMethodName());
		logger.info("Test name is " + name);
		try (DockerNetwork network = DockerNetwork.builder().numNodes(4).testName(name).build()) {
			network.startBlocking();
			String veth = CmdHelper.getVethByContainerName(network.getNodeIds().stream().findFirst().get());
			CmdHelper.setupQueueQuality(veth,"delay 100ms loss 100%");

			RemoteBFTTest test = slowNodeTestBuilder()
				.network(RemoteBFTNetworkBridge.of(network))
				.waitUntilResponsive()
				.startConsensusOnRun().build();
			test.runBlocking(CmdHelper.getTestDurationInSeconds(), TimeUnit.SECONDS);
		}

	}
}
