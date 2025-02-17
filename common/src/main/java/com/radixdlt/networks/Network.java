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

package com.radixdlt.networks;

import com.radixdlt.crypto.ECKeyPair;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Network {

  /// Public Facing Permanent Networks (0x00 - 0x09)
  // - mainnet
  // - stokenet
  MAINNET(1 /* 0x01 */, "mainnet", "rdx", GenesisSource.providedAsync),
  STOKENET(2 /* 0x02 */, "stokenet", "tdx_2_", GenesisSource.providedAsync),

  /// Babylon Temporary Testnets (0x0a - 0x0f)
  // - adapanet = Babylon Alphanet, after Adapa
  // - nebunet = Babylon Betanet, after Nebuchadnezzar
  // - kisharnet = Babylon RCNetV1, after Kishar (from the Babylonian Creation Story)
  // - ansharnet = Babylon RCNetV2, after Anshar (from the Babylonian Creation Story)
  ADAPANET(10 /* 0x0a */, "adapanet", "tdx_a_", GenesisSource.fromConfiguration),
  NEBUNET(11 /* 0x0b */, "nebunet", "tdx_b_", GenesisSource.fromConfiguration),
  KISHARNET(12 /* 0x0c */, "kisharnet", "tdx_c_", GenesisSource.fromConfiguration),
  ANSHARNET(13 /* 0x0d */, "ansharnet", "tdx_d_", GenesisSource.fromConfiguration),

  /// RDX Development - Semi-permanent Testnets (start with 0x2)
  // - gilganet = Integration, after Gilgamesh
  // - enkinet = Misc Network 1, after Enki / Enkidu
  // - hammunet = Misc Network 2, after Hammurabi
  // - nergalnet = A Network for DevOps testing, after the Mesopotamian god Nergal
  // - mardunet = A staging Network for testing new releases to the primary public environment,
  //              after the Babylonian god Marduk
  GILGANET(32 /* 0x20 */, "gilganet", "tdx_20_", GenesisSource.fromConfiguration),
  ENKINET(33 /* 0x21 */, "enkinet", "tdx_21_", GenesisSource.fromConfiguration),
  HAMMUNET(34 /* 0x22 */, "hammunet", "tdx_22_", GenesisSource.fromConfiguration),
  NERGALNET(35 /* 0x23 */, "nergalnet", "tdx_23_", GenesisSource.fromConfiguration),
  MARDUNET(36 /* 0x24 */, "mardunet", "tdx_24_", GenesisSource.fromConfiguration),

  /// Ephemeral Networks (start with 0xF)
  // - localnet = The network used when running locally in development
  // - inttestnet = The network used when running integration tests
  LOCALNET(240 /* 0xF0 */, "localnet", "loc", GenesisSource.fromConfiguration),
  INTEGRATIONTESTNET(241 /* 0xF1 */, "inttestnet", "test", GenesisSource.fromConfiguration),
  LOCALSIMULATOR(242 /* 0xF1 */, "simulator", "sim", GenesisSource.fromConfiguration);

  // For the Radix Shell to provide a default
  public static final String DefaultHexGenesisTransaction =
      ECKeyPair.generateNew().getPublicKey().toHex();

  private final int intId;
  private final byte byteId;
  private final String logicalName;
  private final String hrpSuffix;
  private final String packageHrp;
  private final String normalComponentHrp;
  private final String accountComponentHrp;
  private final String validatorHrp;
  private final String resourceHrp;
  private final String nodeHrp;
  private final GenesisSource genesisSource;

  Network(int id, String logicalName, String hrpSuffix, GenesisSource genesisSource) {
    if (id <= 0 || id > 255) {
      throw new IllegalArgumentException(
          "Id should be between 1 and 255 so it isn't default(int) = 0 and will fit into a byte if"
              + " we change in future");
    }
    this.intId = id;
    this.byteId = (byte) id;
    this.logicalName = logicalName;
    this.hrpSuffix = hrpSuffix;
    this.packageHrp = "package_" + hrpSuffix;
    this.normalComponentHrp = "component_" + hrpSuffix;
    this.accountComponentHrp = "account_" + hrpSuffix;
    this.validatorHrp = "validator_" + hrpSuffix;
    this.resourceHrp = "resource_" + hrpSuffix;
    this.nodeHrp = "node_" + hrpSuffix;
    this.genesisSource = genesisSource;
  }

  public String getPackageHrp() {
    return packageHrp;
  }

  public String getNormalComponentHrp() {
    return normalComponentHrp;
  }

  public String getAccountComponentHrp() {
    return accountComponentHrp;
  }

  public String getValidatorHrp() {
    return validatorHrp;
  }

  public String getResourceHrp() {
    return resourceHrp;
  }

  public String getNodeHrp() {
    return nodeHrp;
  }

  public int getId() {
    return intId;
  }

  public byte getByteId() {
    return byteId;
  }

  public String getLogicalName() {
    return logicalName;
  }

  public String getHrpSuffix() {
    return hrpSuffix;
  }

  public GenesisSource genesisSource() {
    return genesisSource;
  }

  public static Optional<Network> ofId(int id) {
    return find(network -> network.intId == id);
  }

  public static Network ofIdOrThrow(int networkId) {
    return ofId(networkId)
        .orElseThrow(
            () ->
                new RuntimeException(
                    "Provided Network ID does not match any known networks: " + networkId));
  }

  public static Optional<Network> ofName(String logicalName) {
    return find(network -> network.logicalName.equalsIgnoreCase(logicalName));
  }

  private static Optional<Network> find(Predicate<Network> predicate) {
    return Stream.of(values()).filter(predicate).findAny();
  }
}
