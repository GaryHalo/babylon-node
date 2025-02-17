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
import com.radixdlt.api.core.generated.models.EntityReference;
import com.radixdlt.api.core.generated.models.LocalTypeIndex;
import com.radixdlt.api.core.generated.models.NonFungibleIdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * NonFungibleResourceManagerSubstateAllOf
 */
@JsonPropertyOrder({
  NonFungibleResourceManagerSubstateAllOf.JSON_PROPERTY_NON_FUNGIBLE_ID_TYPE,
  NonFungibleResourceManagerSubstateAllOf.JSON_PROPERTY_TOTAL_SUPPLY,
  NonFungibleResourceManagerSubstateAllOf.JSON_PROPERTY_NON_FUNGIBLE_DATA_TABLE,
  NonFungibleResourceManagerSubstateAllOf.JSON_PROPERTY_NON_FUNGIBLE_DATA_TYPE_INDEX,
  NonFungibleResourceManagerSubstateAllOf.JSON_PROPERTY_NON_FUNGIBLE_DATA_MUTABLE_FIELDS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class NonFungibleResourceManagerSubstateAllOf {
  public static final String JSON_PROPERTY_NON_FUNGIBLE_ID_TYPE = "non_fungible_id_type";
  private NonFungibleIdType nonFungibleIdType;

  public static final String JSON_PROPERTY_TOTAL_SUPPLY = "total_supply";
  private String totalSupply;

  public static final String JSON_PROPERTY_NON_FUNGIBLE_DATA_TABLE = "non_fungible_data_table";
  private EntityReference nonFungibleDataTable;

  public static final String JSON_PROPERTY_NON_FUNGIBLE_DATA_TYPE_INDEX = "non_fungible_data_type_index";
  private LocalTypeIndex nonFungibleDataTypeIndex;

  public static final String JSON_PROPERTY_NON_FUNGIBLE_DATA_MUTABLE_FIELDS = "non_fungible_data_mutable_fields";
  private List<String> nonFungibleDataMutableFields = new ArrayList<>();

  public NonFungibleResourceManagerSubstateAllOf() { 
  }

  public NonFungibleResourceManagerSubstateAllOf nonFungibleIdType(NonFungibleIdType nonFungibleIdType) {
    this.nonFungibleIdType = nonFungibleIdType;
    return this;
  }

   /**
   * Get nonFungibleIdType
   * @return nonFungibleIdType
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_ID_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public NonFungibleIdType getNonFungibleIdType() {
    return nonFungibleIdType;
  }


  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_ID_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNonFungibleIdType(NonFungibleIdType nonFungibleIdType) {
    this.nonFungibleIdType = nonFungibleIdType;
  }


  public NonFungibleResourceManagerSubstateAllOf totalSupply(String totalSupply) {
    this.totalSupply = totalSupply;
    return this;
  }

   /**
   * The string-encoded decimal representing the total supply of this resource. A decimal is formed of some signed integer &#x60;m&#x60; of attos (&#x60;10^(-18)&#x60;) units, where &#x60;-2^(256 - 1) &lt;&#x3D; m &lt; 2^(256 - 1)&#x60;. 
   * @return totalSupply
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The string-encoded decimal representing the total supply of this resource. A decimal is formed of some signed integer `m` of attos (`10^(-18)`) units, where `-2^(256 - 1) <= m < 2^(256 - 1)`. ")
  @JsonProperty(JSON_PROPERTY_TOTAL_SUPPLY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTotalSupply() {
    return totalSupply;
  }


  @JsonProperty(JSON_PROPERTY_TOTAL_SUPPLY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalSupply(String totalSupply) {
    this.totalSupply = totalSupply;
  }


  public NonFungibleResourceManagerSubstateAllOf nonFungibleDataTable(EntityReference nonFungibleDataTable) {
    this.nonFungibleDataTable = nonFungibleDataTable;
    return this;
  }

   /**
   * Get nonFungibleDataTable
   * @return nonFungibleDataTable
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_TABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public EntityReference getNonFungibleDataTable() {
    return nonFungibleDataTable;
  }


  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_TABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNonFungibleDataTable(EntityReference nonFungibleDataTable) {
    this.nonFungibleDataTable = nonFungibleDataTable;
  }


  public NonFungibleResourceManagerSubstateAllOf nonFungibleDataTypeIndex(LocalTypeIndex nonFungibleDataTypeIndex) {
    this.nonFungibleDataTypeIndex = nonFungibleDataTypeIndex;
    return this;
  }

   /**
   * Get nonFungibleDataTypeIndex
   * @return nonFungibleDataTypeIndex
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_TYPE_INDEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalTypeIndex getNonFungibleDataTypeIndex() {
    return nonFungibleDataTypeIndex;
  }


  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_TYPE_INDEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNonFungibleDataTypeIndex(LocalTypeIndex nonFungibleDataTypeIndex) {
    this.nonFungibleDataTypeIndex = nonFungibleDataTypeIndex;
  }


  public NonFungibleResourceManagerSubstateAllOf nonFungibleDataMutableFields(List<String> nonFungibleDataMutableFields) {
    this.nonFungibleDataMutableFields = nonFungibleDataMutableFields;
    return this;
  }

  public NonFungibleResourceManagerSubstateAllOf addNonFungibleDataMutableFieldsItem(String nonFungibleDataMutableFieldsItem) {
    this.nonFungibleDataMutableFields.add(nonFungibleDataMutableFieldsItem);
    return this;
  }

   /**
   * The field names of the NF Metadata which are mutable. 
   * @return nonFungibleDataMutableFields
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The field names of the NF Metadata which are mutable. ")
  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_MUTABLE_FIELDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<String> getNonFungibleDataMutableFields() {
    return nonFungibleDataMutableFields;
  }


  @JsonProperty(JSON_PROPERTY_NON_FUNGIBLE_DATA_MUTABLE_FIELDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNonFungibleDataMutableFields(List<String> nonFungibleDataMutableFields) {
    this.nonFungibleDataMutableFields = nonFungibleDataMutableFields;
  }


  /**
   * Return true if this NonFungibleResourceManagerSubstate_allOf object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NonFungibleResourceManagerSubstateAllOf nonFungibleResourceManagerSubstateAllOf = (NonFungibleResourceManagerSubstateAllOf) o;
    return Objects.equals(this.nonFungibleIdType, nonFungibleResourceManagerSubstateAllOf.nonFungibleIdType) &&
        Objects.equals(this.totalSupply, nonFungibleResourceManagerSubstateAllOf.totalSupply) &&
        Objects.equals(this.nonFungibleDataTable, nonFungibleResourceManagerSubstateAllOf.nonFungibleDataTable) &&
        Objects.equals(this.nonFungibleDataTypeIndex, nonFungibleResourceManagerSubstateAllOf.nonFungibleDataTypeIndex) &&
        Objects.equals(this.nonFungibleDataMutableFields, nonFungibleResourceManagerSubstateAllOf.nonFungibleDataMutableFields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nonFungibleIdType, totalSupply, nonFungibleDataTable, nonFungibleDataTypeIndex, nonFungibleDataMutableFields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NonFungibleResourceManagerSubstateAllOf {\n");
    sb.append("    nonFungibleIdType: ").append(toIndentedString(nonFungibleIdType)).append("\n");
    sb.append("    totalSupply: ").append(toIndentedString(totalSupply)).append("\n");
    sb.append("    nonFungibleDataTable: ").append(toIndentedString(nonFungibleDataTable)).append("\n");
    sb.append("    nonFungibleDataTypeIndex: ").append(toIndentedString(nonFungibleDataTypeIndex)).append("\n");
    sb.append("    nonFungibleDataMutableFields: ").append(toIndentedString(nonFungibleDataMutableFields)).append("\n");
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

