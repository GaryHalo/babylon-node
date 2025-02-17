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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * EddsaEd25519SignatureAllOf
 */
@JsonPropertyOrder({
  EddsaEd25519SignatureAllOf.JSON_PROPERTY_SIGNATURE_HEX
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class EddsaEd25519SignatureAllOf {
  public static final String JSON_PROPERTY_SIGNATURE_HEX = "signature_hex";
  private String signatureHex;

  public EddsaEd25519SignatureAllOf() { 
  }

  public EddsaEd25519SignatureAllOf signatureHex(String signatureHex) {
    this.signatureHex = signatureHex;
    return this;
  }

   /**
   * A hex-encoded EdDSA Ed25519 signature (64 bytes). This is &#x60;CONCAT(R, s)&#x60; where &#x60;R&#x60; and &#x60;s&#x60; are each 32-bytes in padded big-endian format.
   * @return signatureHex
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "A hex-encoded EdDSA Ed25519 signature (64 bytes). This is `CONCAT(R, s)` where `R` and `s` are each 32-bytes in padded big-endian format.")
  @JsonProperty(JSON_PROPERTY_SIGNATURE_HEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSignatureHex() {
    return signatureHex;
  }


  @JsonProperty(JSON_PROPERTY_SIGNATURE_HEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSignatureHex(String signatureHex) {
    this.signatureHex = signatureHex;
  }


  /**
   * Return true if this EddsaEd25519Signature_allOf object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EddsaEd25519SignatureAllOf eddsaEd25519SignatureAllOf = (EddsaEd25519SignatureAllOf) o;
    return Objects.equals(this.signatureHex, eddsaEd25519SignatureAllOf.signatureHex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(signatureHex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EddsaEd25519SignatureAllOf {\n");
    sb.append("    signatureHex: ").append(toIndentedString(signatureHex)).append("\n");
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

