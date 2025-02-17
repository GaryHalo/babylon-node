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
import com.radixdlt.api.system.generated.models.PeerChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Peer
 */
@JsonPropertyOrder({
  Peer.JSON_PROPERTY_PEER_ID,
  Peer.JSON_PROPERTY_CHANNELS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class Peer {
  public static final String JSON_PROPERTY_PEER_ID = "peer_id";
  private String peerId;

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<PeerChannel> channels = new ArrayList<>();


  public Peer peerId(String peerId) {
    this.peerId = peerId;
    return this;
  }

   /**
   * Get peerId
   * @return peerId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_PEER_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getPeerId() {
    return peerId;
  }


  @JsonProperty(JSON_PROPERTY_PEER_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPeerId(String peerId) {
    this.peerId = peerId;
  }


  public Peer channels(List<PeerChannel> channels) {
    this.channels = channels;
    return this;
  }

  public Peer addChannelsItem(PeerChannel channelsItem) {
    this.channels.add(channelsItem);
    return this;
  }

   /**
   * Get channels
   * @return channels
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<PeerChannel> getChannels() {
    return channels;
  }


  @JsonProperty(JSON_PROPERTY_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setChannels(List<PeerChannel> channels) {
    this.channels = channels;
  }


  /**
   * Return true if this Peer object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Peer peer = (Peer) o;
    return Objects.equals(this.peerId, peer.peerId) &&
        Objects.equals(this.channels, peer.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(peerId, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Peer {\n");
    sb.append("    peerId: ").append(toIndentedString(peerId)).append("\n");
    sb.append("    channels: ").append(toIndentedString(channels)).append("\n");
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

