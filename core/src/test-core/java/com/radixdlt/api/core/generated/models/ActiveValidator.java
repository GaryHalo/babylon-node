/*
 * Babylon Core API - RCnet V1
 * This API is exposed by the Babylon Radix node to give clients access to the Radix Engine, Mempool and State in the node.  It is intended for use by node-runners on a private network, and is not intended to be exposed publicly. Very heavy load may impact the node's function.  This API exposes queries against the node's current state (see `/lts/state/` or `/state/`), and streams of transaction history (under `/lts/stream/` or `/stream`).  If you require queries against snapshots of historical ledger state, you may also wish to consider using the [Gateway API](https://docs-babylon.radixdlt.com/).  ## Integration and forward compatibility guarantees  This version of the Core API belongs to the first release candidate of the Radix Babylon network (\"RCnet-V1\").  Integrators (such as exchanges) are recommended to use the `/lts/` endpoints - they have been designed to be clear and simple for integrators wishing to create and monitor transactions involving fungible transfers to/from accounts.  All endpoints under `/lts/` are guaranteed to be forward compatible to Babylon mainnet launch (and beyond). We may add new fields, but existing fields will not be changed. Assuming the integrating code uses a permissive JSON parser which ignores unknown fields, any additions will not affect existing code.  We give no guarantees that other endpoints will not change before Babylon mainnet launch, although changes are expected to be minimal. 
 *
 * The version of the OpenAPI document: 0.3.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.radixdlt.api.core.generated.models;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.radixdlt.api.core.generated.models.EcdsaSecp256k1PublicKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ActiveValidator
 */
@JsonPropertyOrder({
  ActiveValidator.JSON_PROPERTY_ADDRESS,
  ActiveValidator.JSON_PROPERTY_KEY,
  ActiveValidator.JSON_PROPERTY_STAKE
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ActiveValidator {
  public static final String JSON_PROPERTY_ADDRESS = "address";
  private String address;

  public static final String JSON_PROPERTY_KEY = "key";
  private EcdsaSecp256k1PublicKey key;

  public static final String JSON_PROPERTY_STAKE = "stake";
  private String stake;

  public ActiveValidator() { 
  }

  public ActiveValidator address(String address) {
    this.address = address;
    return this;
  }

   /**
   * The Bech32m-encoded human readable version of the component address
   * @return address
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The Bech32m-encoded human readable version of the component address")
  @JsonProperty(JSON_PROPERTY_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getAddress() {
    return address;
  }


  @JsonProperty(JSON_PROPERTY_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAddress(String address) {
    this.address = address;
  }


  public ActiveValidator key(EcdsaSecp256k1PublicKey key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public EcdsaSecp256k1PublicKey getKey() {
    return key;
  }


  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKey(EcdsaSecp256k1PublicKey key) {
    this.key = key;
  }


  public ActiveValidator stake(String stake) {
    this.stake = stake;
    return this;
  }

   /**
   * A string-encoded decimal representing the validator&#39;s voting power for this epoch. This is a snapshot of the amount of XRD staked to the validator at the start of the epoch. 
   * @return stake
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "A string-encoded decimal representing the validator's voting power for this epoch. This is a snapshot of the amount of XRD staked to the validator at the start of the epoch. ")
  @JsonProperty(JSON_PROPERTY_STAKE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getStake() {
    return stake;
  }


  @JsonProperty(JSON_PROPERTY_STAKE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStake(String stake) {
    this.stake = stake;
  }


  /**
   * Return true if this ActiveValidator object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActiveValidator activeValidator = (ActiveValidator) o;
    return Objects.equals(this.address, activeValidator.address) &&
        Objects.equals(this.key, activeValidator.key) &&
        Objects.equals(this.stake, activeValidator.stake);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, key, stake);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActiveValidator {\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    stake: ").append(toIndentedString(stake)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

