package org.radix.serialization;

import com.radixdlt.common.EUID;
import com.radixdlt.serialization.Serialization;
import com.radixdlt.universe.Universe;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.stubbing.Answer;
import org.radix.modules.Modules;
import org.radix.properties.RuntimeProperties;
import org.radix.shards.ShardSpace;
import org.radix.time.NtpService;
import org.radix.universe.system.LocalSystem;
import org.radix.utils.SystemMetaData;

import java.security.SecureRandom;
import java.security.Security;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public abstract class RadixTest
{
	@BeforeClass
	public static void startRadixTest() {
		TestSetupUtils.installBouncyCastleProvider();

		final SecureRandom secureRandom = new SecureRandom();

		final RuntimeProperties runtimeProperties = mock(RuntimeProperties.class);
		doAnswer(invocation -> invocation.getArgument(1)).when(runtimeProperties).get(any(), any());

		final Universe universe = mock(Universe.class);
		when(universe.getMagic()).thenReturn(2);

		final SystemMetaData systemMetaData = mock(SystemMetaData.class);
		when(systemMetaData.getUID()).thenReturn(new EUID(2));

		final NtpService ntpService = mock(NtpService.class);
		when(ntpService.getUTCTimeMS()).thenAnswer((Answer<Long>) invocation -> System.currentTimeMillis());

		Serialization serialization = Serialization.getDefault();

		final LocalSystem localSystem = mock(LocalSystem.class);
		when(localSystem.getShards()).thenReturn(new ShardSpace(10000, 20000));

		Modules.put(RuntimeProperties.class, runtimeProperties);
		Modules.put(Serialization.class, serialization);
		Modules.put(SecureRandom.class, secureRandom);
		Modules.put(Universe.class, universe);
		Modules.put(SystemMetaData.class, systemMetaData);
		Modules.put(NtpService.class, ntpService);
		Modules.put(LocalSystem.class, localSystem);
	}

	@AfterClass
	public static void finishRadixTest() {
		Modules.remove(RuntimeProperties.class);
		Modules.remove(Serialization.class);
		Modules.remove(SecureRandom.class);
		Modules.remove(Universe.class);
		Modules.remove(SystemMetaData.class);
		Modules.remove(NtpService.class);
		Modules.remove(LocalSystem.class);
	}

}
