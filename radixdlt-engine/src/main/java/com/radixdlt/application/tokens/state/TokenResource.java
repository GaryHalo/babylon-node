/*
 * (C) Copyright 2021 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 */

package com.radixdlt.application.tokens.state;

import com.radixdlt.constraintmachine.exceptions.AuthorizationException;
import com.radixdlt.constraintmachine.Particle;
import com.radixdlt.crypto.ECPublicKey;
import com.radixdlt.identifiers.REAddr;

import java.util.Objects;
import java.util.Optional;

/**
 * Particle representing a fixed supply token definition
 */
public final class TokenResource implements Particle {
	private final REAddr addr;
	private final String name;
	private final String description;
	private final String iconUrl;
	private final String url;
	private final ECPublicKey owner;
	private final boolean isMutable;

	public TokenResource(
		REAddr addr,
		String name,
		String description,
		String iconUrl,
		String url,
		boolean isMutable,
		ECPublicKey owner
	) {
		this.addr = Objects.requireNonNull(addr);
		this.name = Objects.requireNonNull(name);
		this.description = Objects.requireNonNull(description);
		this.iconUrl = Objects.requireNonNull(iconUrl);
		this.url = Objects.requireNonNull(url);

		if (!isMutable && owner != null) {
			throw new IllegalArgumentException("Can't have fixed supply and minter");
		}

		this.isMutable = isMutable;
		this.owner = owner;
	}

	public static TokenResource createFixedSupplyResource(
		REAddr addr,
		String name,
		String description,
		String iconUrl,
		String url
	) {
		return new TokenResource(addr, name, description, iconUrl, url, false, null);
	}

	public static TokenResource createMutableSupplyResource(
		REAddr addr,
		String name,
		String description,
		String iconUrl,
		String url,
		ECPublicKey owner
	) {
		return new TokenResource(addr, name, description, iconUrl, url, true, owner);
	}

	public void verifyMintAuthorization(Optional<ECPublicKey> key) throws AuthorizationException {
		if (!key.flatMap(p -> getOwner().map(p::equals)).orElse(false)) {
			throw new AuthorizationException("Key not authorized: " + key);
		}
	}

	public void verifyBurnAuthorization(Optional<ECPublicKey> key) throws AuthorizationException {
		if (!key.flatMap(p -> getOwner().map(p::equals)).orElse(false)) {
			throw new AuthorizationException("Key not authorized: " + key);
		}
	}

	public Optional<ECPublicKey> getOwner() {
		return Optional.ofNullable(owner);
	}

	public boolean isMutable() {
		return isMutable;
	}

	public REAddr getAddr() {
		return addr;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return String.format("%s[(%s:%s:%s), (%s)]", getClass().getSimpleName(),
			this.addr, name, isMutable, description);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TokenResource)) {
			return false;
		}
		TokenResource that = (TokenResource) o;
		return Objects.equals(addr, that.addr)
			&& Objects.equals(name, that.name)
			&& Objects.equals(description, that.description)
			&& this.isMutable == that.isMutable
			&& Objects.equals(iconUrl, that.iconUrl)
			&& Objects.equals(url, that.url)
			&& Objects.equals(owner, that.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(addr, name, description, isMutable, iconUrl, url, owner);
	}
}
