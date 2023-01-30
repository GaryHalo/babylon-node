/*
 * Babylon Core API
 * This API is exposed by the Babylon Radix node to give clients access to the Radix Engine, Mempool and State in the node. It is intended for use by node-runners on a private network, and is not intended to be exposed publicly. Heavy load may impact the node's function.  If you require queries against historical ledger state, you may also wish to consider using the [Gateway API](https://betanet-gateway.redoc.ly/). 
 *
 * The version of the OpenAPI document: 0.2.0
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
import com.radixdlt.api.core.generated.models.EntityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AddressType
 */
@JsonPropertyOrder({
  AddressType.JSON_PROPERTY_SUBTYPE,
  AddressType.JSON_PROPERTY_HRP_PREFIX,
  AddressType.JSON_PROPERTY_ENTITY_TYPE,
  AddressType.JSON_PROPERTY_ADDRESS_BYTE_PREFIX,
  AddressType.JSON_PROPERTY_ADDRESS_BYTE_LENGTH
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class AddressType {
  /**
   * Gets or Sets subtype
   */
  public enum SubtypeEnum {
    RESOURCE("Resource"),
    
    PACKAGE("Package"),
    
    NORMALCOMPONENT("NormalComponent"),
    
    ACCOUNTCOMPONENT("AccountComponent"),
    
    ECDSASECP256K1VIRTUALACCOUNTCOMPONENT("EcdsaSecp256k1VirtualAccountComponent"),
    
    EDDSAED25519VIRTUALACCOUNTCOMPONENT("EddsaEd25519VirtualAccountComponent"),
    
    IDENTITYCOMPONENT("IdentityComponent"),
    
    ECDSASECP256K1VIRTUALIDENTITYCOMPONENT("EcdsaSecp256k1VirtualIdentityComponent"),
    
    EDDSAED25519VIRTUALIDENTITYCOMPONENT("EddsaEd25519VirtualIdentityComponent"),
    
    EPOCHMANAGER("EpochManager"),
    
    VALIDATOR("Validator"),
    
    CLOCK("Clock"),
    
    ACCESSCONTROLLER("AccessController");

    private String value;

    SubtypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SubtypeEnum fromValue(String value) {
      for (SubtypeEnum b : SubtypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_SUBTYPE = "subtype";
  private SubtypeEnum subtype;

  public static final String JSON_PROPERTY_HRP_PREFIX = "hrp_prefix";
  private String hrpPrefix;

  public static final String JSON_PROPERTY_ENTITY_TYPE = "entity_type";
  private EntityType entityType;

  public static final String JSON_PROPERTY_ADDRESS_BYTE_PREFIX = "address_byte_prefix";
  private Integer addressBytePrefix;

  public static final String JSON_PROPERTY_ADDRESS_BYTE_LENGTH = "address_byte_length";
  private Integer addressByteLength;

  public AddressType() { 
  }

  public AddressType subtype(SubtypeEnum subtype) {
    this.subtype = subtype;
    return this;
  }

   /**
   * Get subtype
   * @return subtype
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_SUBTYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public SubtypeEnum getSubtype() {
    return subtype;
  }


  @JsonProperty(JSON_PROPERTY_SUBTYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSubtype(SubtypeEnum subtype) {
    this.subtype = subtype;
  }


  public AddressType hrpPrefix(String hrpPrefix) {
    this.hrpPrefix = hrpPrefix;
    return this;
  }

   /**
   * Get hrpPrefix
   * @return hrpPrefix
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_HRP_PREFIX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getHrpPrefix() {
    return hrpPrefix;
  }


  @JsonProperty(JSON_PROPERTY_HRP_PREFIX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setHrpPrefix(String hrpPrefix) {
    this.hrpPrefix = hrpPrefix;
  }


  public AddressType entityType(EntityType entityType) {
    this.entityType = entityType;
    return this;
  }

   /**
   * Get entityType
   * @return entityType
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_ENTITY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public EntityType getEntityType() {
    return entityType;
  }


  @JsonProperty(JSON_PROPERTY_ENTITY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }


  public AddressType addressBytePrefix(Integer addressBytePrefix) {
    this.addressBytePrefix = addressBytePrefix;
    return this;
  }

   /**
   * Get addressBytePrefix
   * minimum: 0
   * maximum: 255
   * @return addressBytePrefix
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_ADDRESS_BYTE_PREFIX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getAddressBytePrefix() {
    return addressBytePrefix;
  }


  @JsonProperty(JSON_PROPERTY_ADDRESS_BYTE_PREFIX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAddressBytePrefix(Integer addressBytePrefix) {
    this.addressBytePrefix = addressBytePrefix;
  }


  public AddressType addressByteLength(Integer addressByteLength) {
    this.addressByteLength = addressByteLength;
    return this;
  }

   /**
   * Get addressByteLength
   * minimum: 0
   * maximum: 255
   * @return addressByteLength
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_ADDRESS_BYTE_LENGTH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getAddressByteLength() {
    return addressByteLength;
  }


  @JsonProperty(JSON_PROPERTY_ADDRESS_BYTE_LENGTH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAddressByteLength(Integer addressByteLength) {
    this.addressByteLength = addressByteLength;
  }


  /**
   * Return true if this AddressType object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressType addressType = (AddressType) o;
    return Objects.equals(this.subtype, addressType.subtype) &&
        Objects.equals(this.hrpPrefix, addressType.hrpPrefix) &&
        Objects.equals(this.entityType, addressType.entityType) &&
        Objects.equals(this.addressBytePrefix, addressType.addressBytePrefix) &&
        Objects.equals(this.addressByteLength, addressType.addressByteLength);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subtype, hrpPrefix, entityType, addressBytePrefix, addressByteLength);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressType {\n");
    sb.append("    subtype: ").append(toIndentedString(subtype)).append("\n");
    sb.append("    hrpPrefix: ").append(toIndentedString(hrpPrefix)).append("\n");
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
    sb.append("    addressBytePrefix: ").append(toIndentedString(addressBytePrefix)).append("\n");
    sb.append("    addressByteLength: ").append(toIndentedString(addressByteLength)).append("\n");
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

