
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
 * Purge Request Body
 * <p>
 * V3 Purge response schema
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "describedBy",
    "detail",
    "estimatedSeconds",
    "httpStatus",
    "purgeId",
    "supportId",
    "title"
})
public class Response {

    /**
     * The URL for the API's machine readable documentation, for example, `https://api.ccu.akamai.com/ccu/v2/errors/internal-error`. It describes the error code in more detail.
     * 
     */
    @JsonProperty("describedBy")
    @JsonPropertyDescription("The URL for the API's machine readable documentation, for example, `https://api.ccu.akamai.com/ccu/v2/errors/internal-error`. It describes the error code in more detail.")
    private String describedBy;
    /**
     * Detailed information about the HTTP status code returned with the response.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    @JsonPropertyDescription("Detailed information about the HTTP status code returned with the response.")
    private String detail;
    /**
     * The estimated number of seconds before the purge is to complete.
     * 
     */
    @JsonProperty("estimatedSeconds")
    @JsonPropertyDescription("The estimated number of seconds before the purge is to complete.")
    private Integer estimatedSeconds;
    /**
     * The HTTP code that indicates the status of the request to invalidate or purge content. Successful requests yield a `201` code.
     * (Required)
     * 
     */
    @JsonProperty("httpStatus")
    @JsonPropertyDescription("The HTTP code that indicates the status of the request to invalidate or purge content. Successful requests yield a `201` code.")
    private Integer httpStatus;
    /**
     * Unique identifier for the purge request.
     * 
     */
    @JsonProperty("purgeId")
    @JsonPropertyDescription("Unique identifier for the purge request.")
    private String purgeId;
    /**
     * Identifier to provide Akamai Technical Support if issues arise.
     * (Required)
     * 
     */
    @JsonProperty("supportId")
    @JsonPropertyDescription("Identifier to provide Akamai Technical Support if issues arise.")
    private String supportId;
    /**
     * Describes the response type, for example, `Rate Limit exceeded`.
     * 
     */
    @JsonProperty("title")
    @JsonPropertyDescription("Describes the response type, for example, `Rate Limit exceeded`.")
    private String title;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The URL for the API's machine readable documentation, for example, `https://api.ccu.akamai.com/ccu/v2/errors/internal-error`. It describes the error code in more detail.
     * 
     */
    @JsonProperty("describedBy")
    public String getDescribedBy() {
        return describedBy;
    }

    /**
     * The URL for the API's machine readable documentation, for example, `https://api.ccu.akamai.com/ccu/v2/errors/internal-error`. It describes the error code in more detail.
     * 
     */
    @JsonProperty("describedBy")
    public void setDescribedBy(String describedBy) {
        this.describedBy = describedBy;
    }

    /**
     * Detailed information about the HTTP status code returned with the response.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     * Detailed information about the HTTP status code returned with the response.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * The estimated number of seconds before the purge is to complete.
     * 
     */
    @JsonProperty("estimatedSeconds")
    public Integer getEstimatedSeconds() {
        return estimatedSeconds;
    }

    /**
     * The estimated number of seconds before the purge is to complete.
     * 
     */
    @JsonProperty("estimatedSeconds")
    public void setEstimatedSeconds(Integer estimatedSeconds) {
        this.estimatedSeconds = estimatedSeconds;
    }

    /**
     * The HTTP code that indicates the status of the request to invalidate or purge content. Successful requests yield a `201` code.
     * (Required)
     * 
     */
    @JsonProperty("httpStatus")
    public Integer getHttpStatus() {
        return httpStatus;
    }

    /**
     * The HTTP code that indicates the status of the request to invalidate or purge content. Successful requests yield a `201` code.
     * (Required)
     * 
     */
    @JsonProperty("httpStatus")
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Unique identifier for the purge request.
     * 
     */
    @JsonProperty("purgeId")
    public String getPurgeId() {
        return purgeId;
    }

    /**
     * Unique identifier for the purge request.
     * 
     */
    @JsonProperty("purgeId")
    public void setPurgeId(String purgeId) {
        this.purgeId = purgeId;
    }

    /**
     * Identifier to provide Akamai Technical Support if issues arise.
     * (Required)
     * 
     */
    @JsonProperty("supportId")
    public String getSupportId() {
        return supportId;
    }

    /**
     * Identifier to provide Akamai Technical Support if issues arise.
     * (Required)
     * 
     */
    @JsonProperty("supportId")
    public void setSupportId(String supportId) {
        this.supportId = supportId;
    }

    /**
     * Describes the response type, for example, `Rate Limit exceeded`.
     * 
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Describes the response type, for example, `Rate Limit exceeded`.
     * 
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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
        return new ToStringBuilder(this).append("describedBy", describedBy).append("detail", detail).append("estimatedSeconds", estimatedSeconds).append("httpStatus", httpStatus).append("purgeId", purgeId).append("supportId", supportId).append("title", title).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(purgeId).append(estimatedSeconds).append(httpStatus).append(describedBy).append(detail).append(supportId).append(additionalProperties).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Response) == false) {
            return false;
        }
        Response rhs = ((Response) other);
        return new EqualsBuilder().append(purgeId, rhs.purgeId).append(estimatedSeconds, rhs.estimatedSeconds).append(httpStatus, rhs.httpStatus).append(describedBy, rhs.describedBy).append(detail, rhs.detail).append(supportId, rhs.supportId).append(additionalProperties, rhs.additionalProperties).append(title, rhs.title).isEquals();
    }

}
