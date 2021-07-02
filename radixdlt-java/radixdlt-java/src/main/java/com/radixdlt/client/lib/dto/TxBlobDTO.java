/*
 * (C) Copyright 2021 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.radixdlt.client.lib.dto;

import org.bouncycastle.util.encoders.Hex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.radixdlt.identifiers.AID;

import static java.util.Objects.requireNonNull;

public class TxBlobDTO {
	private final AID txId;
	private final byte[] blob;

	private TxBlobDTO(AID txId, byte[] blob) {
		this.txId = txId;
		this.blob = blob;
	}

	@JsonCreator
	public static TxBlobDTO create(
		@JsonProperty(value = "txID", required = true) AID txId,
		@JsonProperty(value = "blob", required = true) String blob
	) {
		requireNonNull(txId);
		requireNonNull(blob);

		return new TxBlobDTO(txId, Hex.decode(blob));
	}

	public AID getTxId() {
		return txId;
	}

	public byte[] getBlob() {
		return blob;
	}

	@Override
	public String toString() {
		return "Tx(" + txId.toJson() + ", " + Hex.toHexString(blob) + ')';
	}
}
