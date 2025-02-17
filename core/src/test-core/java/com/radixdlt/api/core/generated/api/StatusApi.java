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

package com.radixdlt.api.core.generated.api;

import com.radixdlt.api.core.generated.client.ApiClient;
import com.radixdlt.api.core.generated.client.ApiException;
import com.radixdlt.api.core.generated.client.ApiResponse;
import com.radixdlt.api.core.generated.client.Pair;

import com.radixdlt.api.core.generated.models.BasicErrorResponse;
import com.radixdlt.api.core.generated.models.NetworkConfigurationResponse;
import com.radixdlt.api.core.generated.models.NetworkStatusRequest;
import com.radixdlt.api.core.generated.models.NetworkStatusResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class StatusApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public StatusApi() {
    this(new ApiClient());
  }

  public StatusApi(ApiClient apiClient) {
    memberVarHttpClient = apiClient.getHttpClient();
    memberVarObjectMapper = apiClient.getObjectMapper();
    memberVarBaseUri = apiClient.getBaseUri();
    memberVarInterceptor = apiClient.getRequestInterceptor();
    memberVarReadTimeout = apiClient.getReadTimeout();
    memberVarResponseInterceptor = apiClient.getResponseInterceptor();
    memberVarAsyncResponseInterceptor = apiClient.getAsyncResponseInterceptor();
  }

  protected ApiException getApiException(String operationId, HttpResponse<InputStream> response) throws IOException {
    String body = response.body() == null ? null : new String(response.body().readAllBytes());
    String message = formatExceptionMessage(operationId, response.statusCode(), body);
    return new ApiException(response.statusCode(), message, response.headers(), body);
  }

  private String formatExceptionMessage(String operationId, int statusCode, String body) {
    if (body == null || body.isEmpty()) {
      body = "[no body]";
    }
    return operationId + " call failed with: " + statusCode + " - " + body;
  }

  /**
   * Get Network Configuration
   * Returns the network configuration of the network the node is connected to.
   * @return NetworkConfigurationResponse
   * @throws ApiException if fails to make API call
   */
  public NetworkConfigurationResponse statusNetworkConfigurationPost() throws ApiException {
    ApiResponse<NetworkConfigurationResponse> localVarResponse = statusNetworkConfigurationPostWithHttpInfo();
    return localVarResponse.getData();
  }

  /**
   * Get Network Configuration
   * Returns the network configuration of the network the node is connected to.
   * @return ApiResponse&lt;NetworkConfigurationResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<NetworkConfigurationResponse> statusNetworkConfigurationPostWithHttpInfo() throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = statusNetworkConfigurationPostRequestBuilder();
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      try {
        if (localVarResponse.statusCode()/ 100 != 2) {
          throw getApiException("statusNetworkConfigurationPost", localVarResponse);
        }
        return new ApiResponse<NetworkConfigurationResponse>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<NetworkConfigurationResponse>() {}) // closes the InputStream
          
        );
      } finally {
      }
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder statusNetworkConfigurationPostRequestBuilder() throws ApiException {

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/status/network-configuration";

    localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * Get Network Status
   * Returns the current state and status of the node&#39;s copy of the ledger.
   * @param networkStatusRequest  (required)
   * @return NetworkStatusResponse
   * @throws ApiException if fails to make API call
   */
  public NetworkStatusResponse statusNetworkStatusPost(NetworkStatusRequest networkStatusRequest) throws ApiException {
    ApiResponse<NetworkStatusResponse> localVarResponse = statusNetworkStatusPostWithHttpInfo(networkStatusRequest);
    return localVarResponse.getData();
  }

  /**
   * Get Network Status
   * Returns the current state and status of the node&#39;s copy of the ledger.
   * @param networkStatusRequest  (required)
   * @return ApiResponse&lt;NetworkStatusResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<NetworkStatusResponse> statusNetworkStatusPostWithHttpInfo(NetworkStatusRequest networkStatusRequest) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = statusNetworkStatusPostRequestBuilder(networkStatusRequest);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      try {
        if (localVarResponse.statusCode()/ 100 != 2) {
          throw getApiException("statusNetworkStatusPost", localVarResponse);
        }
        return new ApiResponse<NetworkStatusResponse>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<NetworkStatusResponse>() {}) // closes the InputStream
          
        );
      } finally {
      }
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder statusNetworkStatusPostRequestBuilder(NetworkStatusRequest networkStatusRequest) throws ApiException {
    // verify the required parameter 'networkStatusRequest' is set
    if (networkStatusRequest == null) {
      throw new ApiException(400, "Missing the required parameter 'networkStatusRequest' when calling statusNetworkStatusPost");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/status/network-status";

    localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

    localVarRequestBuilder.header("Content-Type", "application/json");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(networkStatusRequest);
      localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
    } catch (IOException e) {
      throw new ApiException(e);
    }
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
}
