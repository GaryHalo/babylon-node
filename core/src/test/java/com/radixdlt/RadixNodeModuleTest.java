/* Copyright 2021 Radix Publishing Ltd incorporated in Jersey (Channel Islands).
 *
 * Licensed under the Radix License, Version 1.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * radixfoundation.org/licenses/LICENSE-v1
 *
 * The Licensor hereby grants permission for the Canonical version of the Work to be
 * published, distributed and used under or by reference to the Licensor’s trademark
 * Radix ® and use of any unregistered trade names, logos or get-up.
 *
 * The Licensor provides the Work (and each Contributor provides its Contributions) on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT,
 * MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Whilst the Work is capable of being deployed, used and adopted (instantiated) to create
 * a distributed ledger it is your responsibility to test and validate the code, together
 * with all logic and performance of that code under all foreseeable scenarios.
 *
 * The Licensor does not make or purport to make and hereby excludes liability for all
 * and any representation, warranty or undertaking in any form whatsoever, whether express
 * or implied, to any entity or person, including any representation, warranty or
 * undertaking, as to the functionality security use, value or other characteristics of
 * any distributed ledger nor in respect the functioning or value of any tokens which may
 * be created stored or transferred using the Work. The Licensor does not warrant that the
 * Work or any use of the Work complies with any law or regulation in any territory where
 * it may be implemented or used or that it will be appropriate for any specific purpose.
 *
 * Neither the licensor nor any current or former employees, officers, directors, partners,
 * trustees, representatives, agents, advisors, contractors, or volunteers of the Licensor
 * shall be liable for any direct or indirect, special, incidental, consequential or other
 * losses of any kind, in tort, contract or otherwise (including but not limited to loss
 * of revenue, income or profits, or loss of use or data, or loss of reputation, or loss
 * of any economic or other opportunity of whatsoever nature or howsoever arising), arising
 * out of or in connection with (without limitation of any use, misuse, of any ledger system
 * or use made or its functionality or any performance or operation of any code or protocol
 * caused by bugs or programming or logic errors or otherwise);
 *
 * A. any offer, purchase, holding, use, sale, exchange or transmission of any
 * cryptographic keys, tokens or assets created, exchanged, stored or arising from any
 * interaction with the Work;
 *
 * B. any failure in a transmission or loss of any token or assets keys or other digital
 * artefacts due to errors in transmission;
 *
 * C. bugs, hacks, logic errors or faults in the Work or any communication;
 *
 * D. system software or apparatus including but not limited to losses caused by errors
 * in holding or transmitting tokens by any third-party;
 *
 * E. breaches or failure of security including hacker attacks, loss or disclosure of
 * password, loss of private key, unauthorised use or misuse of such passwords or keys;
 *
 * F. any losses including loss of anticipated savings or other benefits resulting from
 * use of the Work or any changes to the Work (however implemented).
 *
 * You are solely responsible for; testing, validating and evaluation of all operation
 * logic, functionality, security and appropriateness of using the Work for any commercial
 * or non-commercial purpose and for any reproduction or redistribution by You of the
 * Work. You assume all risks associated with Your use of the Work and the exercise of
 * permissions under this License.
 */

package com.radixdlt;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

import com.google.inject.Guice;
import com.radixdlt.crypto.ECKeyPair;
import com.radixdlt.crypto.RadixKeyStore;
import com.radixdlt.networks.Network;
import com.radixdlt.networks.NetworkId;
import com.radixdlt.serialization.TestSetupUtils;
import com.radixdlt.utils.properties.RuntimeProperties;
import java.io.File;
import org.apache.commons.cli.ParseException;
import org.assertj.core.util.Files;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RadixNodeModuleTest {
  @Rule public TemporaryFolder folder = new TemporaryFolder();

  private final String MOCK_GENESIS_TXN =
      ECKeyPair.fromSeed(new byte[] {0x01}).getPublicKey().toHex();

  @NetworkId private int networkId;

  @BeforeClass
  public static void beforeClass() {
    TestSetupUtils.installBouncyCastleProvider();
  }

  @Test
  public void testInjectorNotNullToken() {
    final var properties = createDefaultProperties();
    when(properties.get("network.id")).thenReturn("" + Network.INTEGRATIONTESTNET.getId());
    when(properties.get("network.genesis_txn")).thenReturn(MOCK_GENESIS_TXN);
    when(properties.get("db.location")).thenReturn(folder.getRoot().getAbsolutePath());
    Guice.createInjector(new RadixNodeModule(properties)).injectMembers(this);
  }

  @Test
  public void when_capabilities_ledger_sync_enabled_value_is_invalid_exception_is_thrown() {
    final var properties = createDefaultProperties();
    when(properties.get("network.id")).thenReturn("" + Network.INTEGRATIONTESTNET.getId());
    when(properties.get("network.genesis_txn")).thenReturn(MOCK_GENESIS_TXN);
    when(properties.get("db.location")).thenReturn(folder.getRoot().getAbsolutePath());
    when(properties.get("capabilities.ledger_sync.enabled")).thenReturn("yes");

    Exception exception =
        assertThrows(
            com.google.inject.CreationException.class,
            () -> Guice.createInjector(new RadixNodeModule(properties)).injectMembers(this));

    assertTrue(exception.getCause() instanceof IllegalArgumentException);
    assertEquals(
        "There was an error when parsing configuration 'capabilities.ledger_sync.enabled' with"
            + " value 'yes'.",
        exception.getCause().getMessage());
  }

  @Test
  public void when_capabilities_ledger_sync_enabled_value_is_valid_no_exception_is_thrown() {
    final var properties = createDefaultProperties();
    when(properties.get("network.id")).thenReturn("" + Network.INTEGRATIONTESTNET.getId());
    when(properties.get("network.genesis_txn")).thenReturn(MOCK_GENESIS_TXN);
    when(properties.get("db.location")).thenReturn(folder.getRoot().getAbsolutePath());
    when(properties.get("capabilities.ledger_sync.enabled")).thenReturn("true");

    Guice.createInjector(new RadixNodeModule(properties)).injectMembers(this);
  }

  private RuntimeProperties createDefaultProperties() {
    final RuntimeProperties properties;
    try {
      // Changing it to a spy as it is the only to test polymorphism with mockito.
      properties = spy(new RuntimeProperties(new JSONObject(), new String[0]));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    doReturn("127.0.0.1").when(properties).get(eq("host.ip"), anyString());
    var keyStore = new File("nonesuch.ks");
    Files.delete(keyStore);
    generateKeystore(keyStore);

    doReturn("nonesuch.ks").when(properties).get(eq("node.key.path"), anyString());
    return properties;
  }

  private void generateKeystore(File keyStore) {
    try {
      RadixKeyStore.fromFile(keyStore, null, true).writeKeyPair("node", ECKeyPair.generateNew());
    } catch (Exception e) {
      throw new IllegalStateException("Unable to create keystore");
    }
  }
}
