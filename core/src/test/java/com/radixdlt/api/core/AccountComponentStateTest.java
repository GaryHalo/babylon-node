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

package com.radixdlt.api.core;

import static com.radixdlt.harness.predicates.EventPredicate.onlyConsensusEvents;
import static com.radixdlt.harness.predicates.EventPredicate.onlyLocalMempoolAddEvents;
import static com.radixdlt.harness.predicates.NodesPredicate.allCommittedTransaction;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.radixdlt.api.DeterministicCoreApiTestBase;
import com.radixdlt.api.core.generated.models.ObjectTypeInfoDetails;
import com.radixdlt.api.core.generated.models.StateComponentRequest;
import com.radixdlt.api.core.generated.models.TransactionReceiptRequest;
import com.radixdlt.api.core.generated.models.TypeInfoSubstate;
import com.radixdlt.crypto.ECKeyPair;
import com.radixdlt.crypto.HashUtils;
import com.radixdlt.environment.EventDispatcher;
import com.radixdlt.mempool.MempoolAdd;
import com.radixdlt.rev2.REv2TestTransactions;
import com.radixdlt.rev2.TransactionHeader;
import com.radixdlt.transaction.TransactionBuilder;
import com.radixdlt.transactions.RawNotarizedTransaction;
import com.radixdlt.utils.UInt32;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public final class AccountComponentStateTest extends DeterministicCoreApiTestBase {
  @Test
  public void test_core_api_can_retrieve_account_component_state() throws Exception {
    try (var test = buildRunningServerTest()) {

      // Prepare an account creation transaction
      final var notary = ECKeyPair.generateNew();
      final var header =
          TransactionHeader.defaults(
              networkDefinition,
              1,
              1,
              1,
              notary.getPublicKey().toPublicKey(),
              UInt32.fromNonNegativeInt(10000000),
              true);

      final var manifest = REv2TestTransactions.constructNewAccountManifest(networkDefinition);
      final var intent =
          TransactionBuilder.createIntent(networkDefinition, header, manifest, List.of());
      final var intentHash = HashUtils.blake2b256(intent);
      final var signedIntentBytes = TransactionBuilder.createSignedIntentBytes(intent, List.of());
      final var signedIntentHash = HashUtils.blake2b256(signedIntentBytes).asBytes();
      final var notarySignature = notary.sign(signedIntentHash).toSignature();
      final var notarizedTxBytes =
          TransactionBuilder.createNotarizedBytes(signedIntentBytes, notarySignature);
      final var rawNotarizedTx = RawNotarizedTransaction.create(notarizedTxBytes);

      // Submit an account creation transaction and await its commit
      test.getInstance(0, Key.get(new TypeLiteral<EventDispatcher<MempoolAdd>>() {}))
          .dispatch(MempoolAdd.create(rawNotarizedTx));
      test.runUntilState(
          allCommittedTransaction(rawNotarizedTx),
          onlyConsensusEvents().or(onlyLocalMempoolAddEvents()));

      final var receipt =
          getTransactionApi()
              .transactionReceiptPost(
                  new TransactionReceiptRequest()
                      .network(networkLogicalName)
                      .intentHash(Hex.toHexString(intentHash.asBytes())));

      final var newAccountAddress =
          receipt
              .getCommitted()
              .getReceipt()
              .getStateUpdates()
              .getNewGlobalEntities()
              .get(0)
              .getGlobalAddress();

      final var stateResp =
          getStateApi()
              .stateComponentPost(
                  new StateComponentRequest()
                      .network(networkLogicalName)
                      .componentAddress(newAccountAddress));

      final var substateDetails =
          (ObjectTypeInfoDetails) ((TypeInfoSubstate) stateResp.getInfo()).getDetails();

      // Assert that the component state request succeeds
      assertThat(substateDetails.getBlueprintName()).isEqualTo("Account");
    }
  }
}
