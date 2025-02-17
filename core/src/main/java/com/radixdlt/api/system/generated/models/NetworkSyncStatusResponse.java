/*
 * Babylon System API - RCnet V1
 * This API is exposed by the Babylon Radix node to give clients access to information about the node itself, its configuration, status and subsystems.  It is intended for use by node-runners on a private network, and is not intended to be exposed publicly. Heavy load may impact the node's function.  If you require queries against ledger state, you may also wish to consider using the [Core API or Gateway API instead](https://docs-babylon.radixdlt.com/main/apis/api-specification.html).  ## Integration and forward compatibility guarantees  We give no guarantees that endpoints will not change before Babylon mainnet launch, although changes are expected to be minimal. 
 *
 * The version of the OpenAPI document: 0.3.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.radixdlt.api.system.generated.models;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.radixdlt.api.system.generated.models.SyncStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * NetworkSyncStatusResponse
 */
@JsonPropertyOrder({
  NetworkSyncStatusResponse.JSON_PROPERTY_SYNC_STATUS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class NetworkSyncStatusResponse {
  public static final String JSON_PROPERTY_SYNC_STATUS = "sync_status";
  private SyncStatus syncStatus;


  public NetworkSyncStatusResponse syncStatus(SyncStatus syncStatus) {
    this.syncStatus = syncStatus;
    return this;
  }

   /**
   * Get syncStatus
   * @return syncStatus
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_SYNC_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public SyncStatus getSyncStatus() {
    return syncStatus;
  }


  @JsonProperty(JSON_PROPERTY_SYNC_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSyncStatus(SyncStatus syncStatus) {
    this.syncStatus = syncStatus;
  }


  /**
   * Return true if this NetworkSyncStatusResponse object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NetworkSyncStatusResponse networkSyncStatusResponse = (NetworkSyncStatusResponse) o;
    return Objects.equals(this.syncStatus, networkSyncStatusResponse.syncStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(syncStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetworkSyncStatusResponse {\n");
    sb.append("    syncStatus: ").append(toIndentedString(syncStatus)).append("\n");
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

