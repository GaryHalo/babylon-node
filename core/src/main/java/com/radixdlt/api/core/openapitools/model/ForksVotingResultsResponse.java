/*
 * Radix Core API
 * This API provides endpoints from a node for integration with the Radix ledger.  # Overview  > WARNING > > The Core API is __NOT__ intended to be available on the public web. It is > designed to be accessed in a private network.  The Core API is separated into three: * The **Data API** is a read-only api which allows you to view and sync to the state of the ledger. * The **Construction API** allows you to construct and submit a transaction to the network. * The **Key API** allows you to use the keys managed by the node to sign transactions.  The Core API is a low level API primarily designed for network integrations such as exchanges, ledger analytics providers, or hosted ledger data dashboards where detailed ledger data is required and the integrator can be expected to run their node to provide the Core API for their own consumption.  For a higher level API, see the [Gateway API](https://redocly.github.io/redoc/?url=https://raw.githubusercontent.com/radixdlt/radixdlt-network-gateway/main/generation/gateway-api-spec.yaml).  For node monitoring, see the [System API](https://redocly.github.io/redoc/?url=https://raw.githubusercontent.com/radixdlt/radixdlt/main/core/src/main/java/com/radixdlt/api/system/api.yaml).  ## Rosetta  The Data API and Construction API is inspired from [Rosetta API](https://www.rosetta-api.org/) most notably:   * Use of a JSON-Based RPC protocol on top of HTTP Post requests   * Use of Operations, Amounts, and Identifiers as universal language to   express asset movement for reading and writing  There are a few notable exceptions to note:   * Fetching of ledger data is through a Transaction stream rather than a   Block stream   * Use of `EntityIdentifier` rather than `AccountIdentifier`   * Use of `OperationGroup` rather than `related_operations` to express related   operations   * Construction endpoints perform coin selection on behalf of the caller.   This has the unfortunate effect of not being able to support high frequency   transactions from a single account. This will be addressed in future updates.   * Construction endpoints are online rather than offline as required by Rosetta  Future versions of the api will aim towards a fully-compliant Rosetta API.  ## Enabling Endpoints  All endpoints are enabled when running a node with the exception of two endpoints, each of which need to be manually configured to access: * `/transactions` endpoint must be enabled with configuration `api.transaction.enable=true`. This is because the transactions endpoint requires additional database storage which may not be needed for users who aren't using this endpoint * `/key/sign` endpoint must be enable with configuration `api.sign.enable=true`. This is a potentially dangerous endpoint if accessible publicly so it must be enabled manually.  ## Client Code Generation  We have found success with generating clients against the [api.yaml specification](https://raw.githubusercontent.com/radixdlt/radixdlt/main/core/src/main/java/com/radixdlt/api/core/api.yaml). See https://openapi-generator.tech/ for more details.  The OpenAPI generator only supports openapi version 3.0.0 at present, but you can change 3.1.0 to 3.0.0 in the first line of the spec without affecting generation.  # Data API Flow  The Data API can be used to synchronize a full or partial view of the ledger, transaction by transaction.  ![Data API Flow](https://raw.githubusercontent.com/radixdlt/radixdlt/feature/update-documentation/core/src/main/java/com/radixdlt/api/core/documentation/data_sequence_flow.png)  # Construction API Flow  The Construction API can be used to construct and submit transactions to the network.  ![Construction API Flow](https://raw.githubusercontent.com/radixdlt/radixdlt/feature/open-api/core/src/main/java/com/radixdlt/api/core/documentation/construction_sequence_flow.png)  Unlike the Rosetta Construction API [specification](https://www.rosetta-api.org/docs/construction_api_introduction.html), this Construction API selects UTXOs on behalf of the caller. This has the unfortunate side effect of not being able to support high frequency transactions from a single account due to UTXO conflicts. This will be addressed in a future release. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.radixdlt.api.core.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ForksVotingResultsResponse
 */
@JsonPropertyOrder({
  ForksVotingResultsResponse.JSON_PROPERTY_FORKS_VOTING_RESULTS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-25T15:32:25.714064249+01:00[Europe/Warsaw]")
public class ForksVotingResultsResponse {
  public static final String JSON_PROPERTY_FORKS_VOTING_RESULTS = "forks_voting_results";
  private List<ForkVotingResult> forksVotingResults = new ArrayList<>();

  public ForksVotingResultsResponse forksVotingResults(List<ForkVotingResult> forksVotingResults) {
    this.forksVotingResults = forksVotingResults;
    return this;
  }

  public ForksVotingResultsResponse addForksVotingResultsItem(ForkVotingResult forksVotingResultsItem) {
    this.forksVotingResults.add(forksVotingResultsItem);
    return this;
  }

   /**
   * Get forksVotingResults
   * @return forksVotingResults
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_FORKS_VOTING_RESULTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<ForkVotingResult> getForksVotingResults() {
    return forksVotingResults;
  }


  @JsonProperty(JSON_PROPERTY_FORKS_VOTING_RESULTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setForksVotingResults(List<ForkVotingResult> forksVotingResults) {
    this.forksVotingResults = forksVotingResults;
  }


  /**
   * Return true if this ForksVotingResultsResponse object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ForksVotingResultsResponse forksVotingResultsResponse = (ForksVotingResultsResponse) o;
    return Objects.equals(this.forksVotingResults, forksVotingResultsResponse.forksVotingResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(forksVotingResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ForksVotingResultsResponse {\n");
    sb.append("    forksVotingResults: ").append(toIndentedString(forksVotingResults)).append("\n");
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

