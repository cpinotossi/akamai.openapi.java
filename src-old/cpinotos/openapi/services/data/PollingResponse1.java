
package cpinotos.openapi.services.data;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Checks the status of asynchronous requests. For details, see [Asynchronous Requests](overview.html#asynchronousrequests).
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "link",
    "requestId",
    "retryAfter"
})
public class PollingResponse1 {

    /**
     * The link for this asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("link")
    @JsonPropertyDescription("The link for this asynchronous request.")
    private String link;
    /**
     * Unique identifier for the asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("requestId")
    @JsonPropertyDescription("Unique identifier for the asynchronous request.")
    private String requestId;
    /**
     * Number of seconds when the request is estimated to have processed.
     * (Required)
     * 
     */
    @JsonProperty("retryAfter")
    @JsonPropertyDescription("Number of seconds when the request is estimated to have processed.")
    private Integer retryAfter;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The link for this asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * The link for this asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Unique identifier for the asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    /**
     * Unique identifier for the asynchronous request.
     * (Required)
     * 
     */
    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Number of seconds when the request is estimated to have processed.
     * (Required)
     * 
     */
    @JsonProperty("retryAfter")
    public Integer getRetryAfter() {
        return retryAfter;
    }

    /**
     * Number of seconds when the request is estimated to have processed.
     * (Required)
     * 
     */
    @JsonProperty("retryAfter")
    public void setRetryAfter(Integer retryAfter) {
        this.retryAfter = retryAfter;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("link", link).append("requestId", requestId).append("retryAfter", retryAfter).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(link).append(additionalProperties).append(retryAfter).append(requestId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PollingResponse1) == false) {
            return false;
        }
        PollingResponse1 rhs = ((PollingResponse1) other);
        return new EqualsBuilder().append(link, rhs.link).append(additionalProperties, rhs.additionalProperties).append(retryAfter, rhs.retryAfter).append(requestId, rhs.requestId).isEquals();
    }

}
