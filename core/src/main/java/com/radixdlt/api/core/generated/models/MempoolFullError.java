/*
 * Babylon Core API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1.0
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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.radixdlt.api.core.generated.models.CoreErrorDetails;
import com.radixdlt.api.core.generated.models.InternalServerError;
import com.radixdlt.api.core.generated.models.InvalidHexError;
import com.radixdlt.api.core.generated.models.InvalidJsonError;
import com.radixdlt.api.core.generated.models.InvalidTransactionError;
import com.radixdlt.api.core.generated.models.MempoolFullError;
import com.radixdlt.api.core.generated.models.MempoolFullErrorAllOf;
import com.radixdlt.api.core.generated.models.NetworkNotSupportedError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import com.radixdlt.api.common.JSON;
/**
 * MempoolFullError
 */
@JsonPropertyOrder({
  MempoolFullError.JSON_PROPERTY_MEMPOOL_TRANSACTION_COUNT
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = InternalServerError.class, name = "InternalServerError"),
  @JsonSubTypes.Type(value = InvalidHexError.class, name = "InvalidHexError"),
  @JsonSubTypes.Type(value = InvalidJsonError.class, name = "InvalidJsonError"),
  @JsonSubTypes.Type(value = InvalidTransactionError.class, name = "InvalidTransactionError"),
  @JsonSubTypes.Type(value = MempoolFullError.class, name = "MempoolFullError"),
  @JsonSubTypes.Type(value = NetworkNotSupportedError.class, name = "NetworkNotSupportedError"),
})

public class MempoolFullError extends CoreErrorDetails {
  public static final String JSON_PROPERTY_MEMPOOL_TRANSACTION_COUNT = "mempool_transaction_count";
  private Integer mempoolTransactionCount;


  public MempoolFullError mempoolTransactionCount(Integer mempoolTransactionCount) {
    this.mempoolTransactionCount = mempoolTransactionCount;
    return this;
  }

   /**
   * Get mempoolTransactionCount
   * @return mempoolTransactionCount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_MEMPOOL_TRANSACTION_COUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getMempoolTransactionCount() {
    return mempoolTransactionCount;
  }


  @JsonProperty(JSON_PROPERTY_MEMPOOL_TRANSACTION_COUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMempoolTransactionCount(Integer mempoolTransactionCount) {
    this.mempoolTransactionCount = mempoolTransactionCount;
  }


  /**
   * Return true if this MempoolFullError object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MempoolFullError mempoolFullError = (MempoolFullError) o;
    return Objects.equals(this.mempoolTransactionCount, mempoolFullError.mempoolTransactionCount) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mempoolTransactionCount, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MempoolFullError {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    mempoolTransactionCount: ").append(toIndentedString(mempoolTransactionCount)).append("\n");
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

static {
  // Initialize and register the discriminator mappings.
  Map<String, Class<?>> mappings = new HashMap<String, Class<?>>();
  mappings.put("InternalServerError", InternalServerError.class);
  mappings.put("InvalidHexError", InvalidHexError.class);
  mappings.put("InvalidJsonError", InvalidJsonError.class);
  mappings.put("InvalidTransactionError", InvalidTransactionError.class);
  mappings.put("MempoolFullError", MempoolFullError.class);
  mappings.put("NetworkNotSupportedError", NetworkNotSupportedError.class);
  mappings.put("MempoolFullError", MempoolFullError.class);
  JSON.registerDiscriminator(MempoolFullError.class, "type", mappings);
}
}

