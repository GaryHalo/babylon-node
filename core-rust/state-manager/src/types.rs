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

use crate::{
    accumulator_tree::IsHash, jni::common_types::JavaHashCode, transaction::LedgerTransaction,
    LedgerTransactionOutcome, LedgerTransactionReceipt, SubstateChanges,
};
use radix_engine::types::*;
use std::fmt;

use transaction::ecdsa_secp256k1::EcdsaSecp256k1Signature;
use transaction::model::{
    NotarizedTransaction, PreviewFlags, SignedTransactionIntent, TransactionIntent,
    TransactionManifest,
};

#[derive(PartialEq, Eq, Hash, Clone, Copy, PartialOrd, Ord, Decode, Encode, Categorize)]
pub struct AccumulatorHash([u8; Self::LENGTH]);

impl AccumulatorHash {
    pub const LENGTH: usize = 32;

    pub fn pre_genesis() -> Self {
        Self([0; Self::LENGTH])
    }

    pub fn accumulate(&self, ledger_payload_hash: &LedgerPayloadHash) -> Self {
        let concat_bytes = [self.0, ledger_payload_hash.0].concat();
        Self(blake2b_256_hash(concat_bytes).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for AccumulatorHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl From<JavaHashCode> for AccumulatorHash {
    fn from(java_hash_code: JavaHashCode) -> Self {
        AccumulatorHash::from_raw_bytes(java_hash_code.into_bytes())
    }
}

impl fmt::Display for AccumulatorHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for AccumulatorHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("AccumulatorHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

#[derive(
    PartialEq,
    Eq,
    Hash,
    Clone,
    Copy,
    PartialOrd,
    Ord,
    ScryptoCategorize,
    ScryptoEncode,
    ScryptoDecode,
)]
pub struct LedgerPayloadHash([u8; Self::LENGTH]);

impl LedgerPayloadHash {
    pub const LENGTH: usize = 32;

    pub fn for_transaction(transaction: &LedgerTransaction) -> Self {
        Self::for_ledger_payload_bytes(&manifest_encode(transaction).unwrap())
    }

    pub fn for_ledger_payload_bytes(ledger_payload_bytes: &[u8]) -> Self {
        Self(blake2b_256_hash(ledger_payload_bytes).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for LedgerPayloadHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl From<Hash> for LedgerPayloadHash {
    fn from(hash: Hash) -> Self {
        LedgerPayloadHash(hash.0)
    }
}

impl fmt::Display for LedgerPayloadHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for LedgerPayloadHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("LedgerPayloadHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

pub trait HasLedgerPayloadHash {
    fn ledger_payload_hash(&self) -> LedgerPayloadHash;
}

impl HasLedgerPayloadHash for LedgerTransaction {
    fn ledger_payload_hash(&self) -> LedgerPayloadHash {
        LedgerPayloadHash::for_transaction(self)
    }
}

impl HasLedgerPayloadHash for NotarizedTransaction {
    fn ledger_payload_hash(&self) -> LedgerPayloadHash {
        // Could optimize this to remove the clone in future,
        // once SBOR/models are more stable
        LedgerTransaction::User(self.clone()).ledger_payload_hash()
    }
}

#[derive(
    PartialEq,
    Eq,
    Hash,
    Clone,
    Copy,
    PartialOrd,
    Ord,
    ScryptoCategorize,
    ScryptoEncode,
    ScryptoDecode,
)]
pub struct LedgerReceiptHash([u8; Self::LENGTH]);

#[derive(ScryptoCategorize, ScryptoEncode)]
struct HashableLedgerReceiptPart {
    successful: bool,
    substate_changes: SubstateChanges,
}

impl LedgerReceiptHash {
    pub const LENGTH: usize = 32;

    pub fn for_receipt(receipt: &LedgerTransactionReceipt) -> Self {
        let hashable_part = HashableLedgerReceiptPart {
            successful: matches!(receipt.outcome, LedgerTransactionOutcome::Success(_)),
            substate_changes: receipt.substate_changes.clone(),
        };
        let hashable_part_bytes = scrypto_encode(&hashable_part).unwrap();
        Self(blake2b_256_hash(hashable_part_bytes).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl fmt::Display for LedgerReceiptHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for LedgerReceiptHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("LedgerReceiptHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

#[derive(
    PartialEq,
    Eq,
    Hash,
    Clone,
    Copy,
    PartialOrd,
    Ord,
    ScryptoCategorize,
    ScryptoEncode,
    ScryptoDecode,
)]
pub struct UserPayloadHash([u8; Self::LENGTH]);

impl UserPayloadHash {
    pub const LENGTH: usize = 32;

    pub fn for_transaction(transaction: &NotarizedTransaction) -> Self {
        Self(blake2b_256_hash(manifest_encode(transaction).unwrap()).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for UserPayloadHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl fmt::Display for UserPayloadHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for UserPayloadHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("UserPayloadHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

pub trait HasUserPayloadHash {
    fn user_payload_hash(&self) -> UserPayloadHash;
}

impl HasUserPayloadHash for NotarizedTransaction {
    fn user_payload_hash(&self) -> UserPayloadHash {
        UserPayloadHash::for_transaction(self)
    }
}

#[derive(
    PartialEq,
    Eq,
    Hash,
    Clone,
    Copy,
    PartialOrd,
    Ord,
    ScryptoCategorize,
    ScryptoEncode,
    ScryptoDecode,
)]
pub struct SignaturesHash([u8; Self::LENGTH]);

impl SignaturesHash {
    pub const LENGTH: usize = 32;

    pub fn for_signed_intent(signed_intent: &SignedTransactionIntent) -> Self {
        Self(blake2b_256_hash(manifest_encode(signed_intent).unwrap()).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for SignaturesHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl fmt::Display for SignaturesHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for SignaturesHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("SignaturesHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

pub trait HasSignaturesHash {
    fn signatures_hash(&self) -> SignaturesHash;
}

impl HasSignaturesHash for SignedTransactionIntent {
    fn signatures_hash(&self) -> SignaturesHash {
        SignaturesHash::for_signed_intent(self)
    }
}

impl HasSignaturesHash for NotarizedTransaction {
    fn signatures_hash(&self) -> SignaturesHash {
        self.signed_intent.signatures_hash()
    }
}

#[derive(PartialEq, Eq, Hash, Clone, Copy, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct IntentHash([u8; Self::LENGTH]);

impl IntentHash {
    pub const LENGTH: usize = 32;

    pub fn for_intent(intent: &TransactionIntent) -> Self {
        Self(blake2b_256_hash(manifest_encode(intent).unwrap()).0)
    }

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for IntentHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl fmt::Display for IntentHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for IntentHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("IntentHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

pub trait HasIntentHash {
    fn intent_hash(&self) -> IntentHash;
}

impl HasIntentHash for TransactionIntent {
    fn intent_hash(&self) -> IntentHash {
        IntentHash::for_intent(self)
    }
}

impl HasIntentHash for SignedTransactionIntent {
    fn intent_hash(&self) -> IntentHash {
        self.intent.intent_hash()
    }
}

impl HasIntentHash for NotarizedTransaction {
    fn intent_hash(&self) -> IntentHash {
        self.signed_intent.intent.intent_hash()
    }
}

#[derive(PartialEq, Eq, Hash, Clone, Copy, PartialOrd, Ord, Categorize, Encode, Decode)]
pub struct StateHash([u8; Self::LENGTH]);

impl StateHash {
    pub const LENGTH: usize = 32;

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for StateHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl From<Hash> for StateHash {
    fn from(hash: Hash) -> Self {
        Self(hash.0)
    }
}

impl fmt::Display for StateHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for StateHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("StateHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

#[derive(PartialEq, Eq, Hash, Clone, Copy, PartialOrd, Ord, Categorize, Encode, Decode)]
pub struct TransactionTreeHash([u8; Self::LENGTH]);

impl TransactionTreeHash {
    pub const LENGTH: usize = 32;

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for TransactionTreeHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl From<Hash> for TransactionTreeHash {
    fn from(hash: Hash) -> Self {
        Self(hash.0)
    }
}

impl IsHash for TransactionTreeHash {}

impl fmt::Display for TransactionTreeHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for TransactionTreeHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("TransactionTreeHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

#[derive(PartialEq, Eq, Hash, Clone, Copy, PartialOrd, Ord, Categorize, Encode, Decode)]
pub struct ReceiptTreeHash([u8; Self::LENGTH]);

impl ReceiptTreeHash {
    pub const LENGTH: usize = 32;

    pub fn from_raw_bytes(hash_bytes: [u8; Self::LENGTH]) -> Self {
        Self(hash_bytes)
    }

    pub fn into_bytes(self) -> [u8; Self::LENGTH] {
        self.0
    }
}

impl AsRef<[u8]> for ReceiptTreeHash {
    fn as_ref(&self) -> &[u8] {
        &self.0
    }
}

impl From<Hash> for ReceiptTreeHash {
    fn from(hash: Hash) -> Self {
        Self(hash.0)
    }
}

impl IsHash for ReceiptTreeHash {}

impl fmt::Display for ReceiptTreeHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", hex::encode(self.0))
    }
}

impl fmt::Debug for ReceiptTreeHash {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_tuple("ReceiptTreeHash")
            .field(&hex::encode(self.0))
            .finish()
    }
}

#[derive(PartialEq, Eq, Hash, Clone, Copy, PartialOrd, Ord, Debug, Categorize, Encode, Decode)]
pub struct LedgerHashes {
    pub state_root: StateHash,
    pub transaction_root: TransactionTreeHash,
    pub receipt_root: ReceiptTreeHash,
}

/// An uncommitted user transaction, in eg the mempool
#[derive(Debug, PartialEq, Eq, Clone)]
pub struct PendingTransaction {
    pub payload: NotarizedTransaction,
    pub payload_hash: UserPayloadHash,
    pub intent_hash: IntentHash,
    pub payload_size: usize,
}

impl From<NotarizedTransaction> for PendingTransaction {
    fn from(transaction: NotarizedTransaction) -> Self {
        let intent_hash = transaction.intent_hash();
        let payload_size = transaction.to_bytes().unwrap().len();
        PendingTransaction {
            payload_hash: transaction.user_payload_hash(),
            intent_hash,
            payload: transaction,
            payload_size,
        }
    }
}

#[derive(Debug, ManifestCategorize, ManifestEncode, ManifestDecode)]
pub struct PreviewRequest {
    pub manifest: TransactionManifest,
    pub start_epoch_inclusive: u64,
    pub end_epoch_exclusive: u64,
    pub notary_public_key: Option<PublicKey>,
    pub notary_as_signatory: bool,
    pub cost_unit_limit: u32,
    pub tip_percentage: u16,
    pub nonce: u64,
    pub signer_public_keys: Vec<PublicKey>,
    pub flags: PreviewFlags,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub enum CommitError {
    MissingEpochProof,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct CommitRequest {
    pub transaction_payloads: Vec<Vec<u8>>,
    pub proof: LedgerProof,
    pub vertex_store: Option<Vec<u8>>,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct PrepareRequest {
    pub parent_accumulator: AccumulatorHash,
    pub prepared_vertices: Vec<PreviousVertex>,
    pub proposed_payloads: Vec<Vec<u8>>,
    pub consensus_epoch: u64,
    pub round_number: u64,
    pub proposer_timestamp_ms: i64,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct PreviousVertex {
    pub transaction_payloads: Vec<Vec<u8>>,
    pub resultant_accumulator: AccumulatorHash,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct PrepareResult {
    pub committed: Vec<Vec<u8>>,
    pub rejected: Vec<(Vec<u8>, String)>,
    pub next_epoch: Option<NextEpoch>,
    pub ledger_hashes: LedgerHashes,
}

#[derive(Debug, Clone, Eq, PartialEq, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct ActiveValidatorInfo {
    pub address: Option<ComponentAddress>,
    pub key: EcdsaSecp256k1PublicKey,
    pub stake: Decimal,
}

#[derive(Debug, Clone, Eq, PartialEq, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct NextEpoch {
    pub validator_set: Vec<ActiveValidatorInfo>,
    pub epoch: u64,
}

#[derive(Debug, Decode, Encode, Categorize)]
pub struct PrepareGenesisRequest {
    pub genesis: Vec<u8>,
}

#[derive(Debug, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct PrepareGenesisResult {
    pub validator_set: Option<Vec<ActiveValidatorInfo>>,
    pub ledger_hashes: LedgerHashes,
}

#[derive(Debug, Clone, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct TimestampedValidatorSignature {
    pub key: EcdsaSecp256k1PublicKey,
    pub validator_address: Option<ComponentAddress>,
    pub timestamp_ms: i64,
    pub signature: EcdsaSecp256k1Signature,
}

#[derive(Debug, Clone, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct LedgerProof {
    pub opaque: Hash,
    pub ledger_header: LedgerHeader,
    pub timestamped_signatures: Vec<TimestampedValidatorSignature>,
}

#[derive(Debug, Clone, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct LedgerHeader {
    pub epoch: u64,
    pub round: u64,
    pub accumulator_state: AccumulatorState,
    pub hashes: LedgerHashes,
    pub consensus_parent_round_timestamp_ms: i64,
    pub proposer_timestamp_ms: i64,
    pub next_epoch: Option<NextEpoch>,
}

#[derive(Debug, Clone, ScryptoCategorize, ScryptoEncode, ScryptoDecode)]
pub struct AccumulatorState {
    pub state_version: u64,
    pub accumulator_hash: AccumulatorHash,
}

pub struct EpochTransactionIdentifiers {
    pub state_version: u64,
    pub transaction_hash: TransactionTreeHash,
    pub receipt_hash: ReceiptTreeHash,
}

impl EpochTransactionIdentifiers {
    pub fn pre_genesis() -> Self {
        Self {
            state_version: 0,
            transaction_hash: TransactionTreeHash([0; TransactionTreeHash::LENGTH]),
            receipt_hash: ReceiptTreeHash([0; TransactionTreeHash::LENGTH]),
        }
    }

    pub fn from(epoch_header: LedgerHeader) -> Self {
        Self {
            state_version: epoch_header.accumulator_state.state_version,
            transaction_hash: epoch_header.hashes.transaction_root,
            receipt_hash: epoch_header.hashes.receipt_root,
        }
    }
}
