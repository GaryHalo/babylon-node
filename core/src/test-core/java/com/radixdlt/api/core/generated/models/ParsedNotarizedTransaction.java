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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.radixdlt.api.core.generated.models.NotarizedTransaction;
import com.radixdlt.api.core.generated.models.ParsedLedgerTransaction;
import com.radixdlt.api.core.generated.models.ParsedNotarizedTransaction;
import com.radixdlt.api.core.generated.models.ParsedNotarizedTransactionAllOf;
import com.radixdlt.api.core.generated.models.ParsedNotarizedTransactionAllOfIdentifiers;
import com.radixdlt.api.core.generated.models.ParsedNotarizedTransactionAllOfValidationError;
import com.radixdlt.api.core.generated.models.ParsedSignedTransactionIntent;
import com.radixdlt.api.core.generated.models.ParsedTransaction;
import com.radixdlt.api.core.generated.models.ParsedTransactionIntent;
import com.radixdlt.api.core.generated.models.ParsedTransactionManifest;
import com.radixdlt.api.core.generated.models.ParsedTransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import com.radixdlt.api.core.generated.client.JSON;
/**
 * ParsedNotarizedTransaction
 */
@JsonPropertyOrder({
  ParsedNotarizedTransaction.JSON_PROPERTY_NOTARIZED_TRANSACTION,
  ParsedNotarizedTransaction.JSON_PROPERTY_IDENTIFIERS,
  ParsedNotarizedTransaction.JSON_PROPERTY_VALIDATION_ERROR
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
@JsonIgnoreProperties(
  value = "type", // ignore manually set type, it will be automatically generated by Jackson during serialization
  allowSetters = true // allows the type to be set during deserialization
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ParsedLedgerTransaction.class, name = "LedgerTransaction"),
  @JsonSubTypes.Type(value = ParsedNotarizedTransaction.class, name = "NotarizedTransaction"),
  @JsonSubTypes.Type(value = ParsedSignedTransactionIntent.class, name = "SignedTransactionIntent"),
  @JsonSubTypes.Type(value = ParsedTransactionIntent.class, name = "TransactionIntent"),
  @JsonSubTypes.Type(value = ParsedTransactionManifest.class, name = "TransactionManifest"),
})

public class ParsedNotarizedTransaction extends ParsedTransaction {
  public static final String JSON_PROPERTY_NOTARIZED_TRANSACTION = "notarized_transaction";
  private NotarizedTransaction notarizedTransaction;

  public static final String JSON_PROPERTY_IDENTIFIERS = "identifiers";
  private ParsedNotarizedTransactionAllOfIdentifiers identifiers;

  public static final String JSON_PROPERTY_VALIDATION_ERROR = "validation_error";
  private ParsedNotarizedTransactionAllOfValidationError validationError;

  public ParsedNotarizedTransaction() { 
  }

  public ParsedNotarizedTransaction notarizedTransaction(NotarizedTransaction notarizedTransaction) {
    this.notarizedTransaction = notarizedTransaction;
    return this;
  }

   /**
   * Get notarizedTransaction
   * @return notarizedTransaction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_NOTARIZED_TRANSACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public NotarizedTransaction getNotarizedTransaction() {
    return notarizedTransaction;
  }


  @JsonProperty(JSON_PROPERTY_NOTARIZED_TRANSACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNotarizedTransaction(NotarizedTransaction notarizedTransaction) {
    this.notarizedTransaction = notarizedTransaction;
  }


  public ParsedNotarizedTransaction identifiers(ParsedNotarizedTransactionAllOfIdentifiers identifiers) {
    this.identifiers = identifiers;
    return this;
  }

   /**
   * Get identifiers
   * @return identifiers
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_IDENTIFIERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public ParsedNotarizedTransactionAllOfIdentifiers getIdentifiers() {
    return identifiers;
  }


  @JsonProperty(JSON_PROPERTY_IDENTIFIERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIdentifiers(ParsedNotarizedTransactionAllOfIdentifiers identifiers) {
    this.identifiers = identifiers;
  }


  public ParsedNotarizedTransaction validationError(ParsedNotarizedTransactionAllOfValidationError validationError) {
    this.validationError = validationError;
    return this;
  }

   /**
   * Get validationError
   * @return validationError
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_VALIDATION_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ParsedNotarizedTransactionAllOfValidationError getValidationError() {
    return validationError;
  }


  @JsonProperty(JSON_PROPERTY_VALIDATION_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValidationError(ParsedNotarizedTransactionAllOfValidationError validationError) {
    this.validationError = validationError;
  }


  /**
   * Return true if this ParsedNotarizedTransaction object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParsedNotarizedTransaction parsedNotarizedTransaction = (ParsedNotarizedTransaction) o;
    return Objects.equals(this.notarizedTransaction, parsedNotarizedTransaction.notarizedTransaction) &&
        Objects.equals(this.identifiers, parsedNotarizedTransaction.identifiers) &&
        Objects.equals(this.validationError, parsedNotarizedTransaction.validationError) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notarizedTransaction, identifiers, validationError, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParsedNotarizedTransaction {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    notarizedTransaction: ").append(toIndentedString(notarizedTransaction)).append("\n");
    sb.append("    identifiers: ").append(toIndentedString(identifiers)).append("\n");
    sb.append("    validationError: ").append(toIndentedString(validationError)).append("\n");
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
  mappings.put("LedgerTransaction", ParsedLedgerTransaction.class);
  mappings.put("NotarizedTransaction", ParsedNotarizedTransaction.class);
  mappings.put("SignedTransactionIntent", ParsedSignedTransactionIntent.class);
  mappings.put("TransactionIntent", ParsedTransactionIntent.class);
  mappings.put("TransactionManifest", ParsedTransactionManifest.class);
  mappings.put("ParsedNotarizedTransaction", ParsedNotarizedTransaction.class);
  JSON.registerDiscriminator(ParsedNotarizedTransaction.class, "type", mappings);
}
}

