/*
 *
 *  * (C) Copyright 2020 Radix DLT Ltd
 *  *
 *  * Radix DLT Ltd licenses this file to you under the Apache License,
 *  * Version 2.0 (the "License"); you may not use this file except in
 *  * compliance with the License.  You may obtain a copy of the
 *  * License at
 *  *
 *  *  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *  * either express or implied.  See the License for the specific
 *  * language governing permissions and limitations under the License.
 *
 */

package com.radixdlt.crypto.hdwallet;

import com.google.common.annotations.VisibleForTesting;
import com.radixdlt.crypto.ECKeyPair;
import com.radixdlt.utils.Bytes;

public final class HDKeyPair {
	private final ECKeyPair ecKeyPair;
	private final String path;
	private final boolean isHardened;
	private final int depth;

	public HDKeyPair(ECKeyPair ecKeyPair, String path) {
		this.ecKeyPair = ecKeyPair;
		this.path = path;
		this.isHardened = path.endsWith("'");
		this.depth = path.split("/").length - 1;
	}

	public ECKeyPair keyPair() {
		return ecKeyPair;
	}

	public String path() {
		return path;
	}

	public boolean isHardened() {
		return isHardened;
	}

	public int depth() {
		return depth;
	}

	@VisibleForTesting
	String privateKeyHex() {
		return Bytes.toHexString(ecKeyPair.getPrivateKey());
	}

	@VisibleForTesting
	String publicKeyHex() {
		return Bytes.toHexString(ecKeyPair.getPublicKey().getBytes());
	}
}
