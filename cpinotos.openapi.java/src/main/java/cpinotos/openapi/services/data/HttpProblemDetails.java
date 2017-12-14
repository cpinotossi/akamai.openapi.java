
package cpinotos.openapi.services.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "detail",
    "errors",
    "instance",
    "method",
    "requestTime",
    "status",
    "title",
    "type"
})
public class HttpProblemDetails {

    /**
     * A detailed message describing the problem.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    @JsonPropertyDescription("A detailed message describing the problem.")
    private String detail;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("errors")
    private List<Error> errors = new ArrayList<Error>();
    /**
     * The instance on which this error occured.
     * (Required)
     * 
     */
    @JsonProperty("instance")
    @JsonPropertyDescription("The instance on which this error occured.")
    private String instance;
    /**
     * The http method for this request.
     * (Required)
     * 
     */
    @JsonProperty("method")
    @JsonPropertyDescription("The http method for this request.")
    private String method;
    /**
     * The request time recorded.
     * (Required)
     * 
     */
    @JsonProperty("requestTime")
    @JsonPropertyDescription("The request time recorded.")
    private String requestTime;
    /**
     * The http status of the request.
     * (Required)
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The http status of the request.")
    private Integer status;
    /**
     * The title of this problem message
     * (Required)
     * 
     */
    @JsonProperty("title")
    @JsonPropertyDescription("The title of this problem message")
    private String title;
    /**
     * The type of http error problem.
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of http error problem.")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A detailed message describing the problem.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     * A detailed message describing the problem.
     * (Required)
     * 
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("errors")
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("errors")
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * The instance on which this error occured.
     * (Required)
     * 
     */
    @JsonProperty("instance")
    public String getInstance() {
        return instance;
    }

    /**
     * The instance on which this error occured.
     * (Required)
     * 
     */
    @JsonProperty("instance")
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * The http method for this request.
     * (Required)
     * 
     */
    @JsonProperty("method")
    public String getMethod() {
        return method;
    }

    /**
     * The http method for this request.
     * (Required)
     * 
     */
    @JsonProperty("method")
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * The request time recorded.
     * (Required)
     * 
     */
    @JsonProperty("requestTime")
    public String getRequestTime() {
        return requestTime;
    }

    /**
     * The request time recorded.
     * (Required)
     * 
     */
    @JsonProperty("requestTime")
    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * The http status of the request.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * The http status of the request.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * The title of this problem message
     * (Required)
     * 
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * The title of this problem message
     * (Required)
     * 
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The type of http error problem.
     * (Required)
     * 
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * The type of http error problem.
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
        return new ToStringBuilder(this).append("detail", detail).append("errors", errors).append("instance", instance).append("method", method).append("requestTime", requestTime).append("status", status).append("title", title).append("type", type).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(requestTime).append(instance).append(method).append(detail).append(additionalProperties).append(title).append(type).append(errors).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HttpProblemDetails) == false) {
            return false;
        }
        HttpProblemDetails rhs = ((HttpProblemDetails) other);
        return new EqualsBuilder().append(requestTime, rhs.requestTime).append(instance, rhs.instance).append(method, rhs.method).append(detail, rhs.detail).append(additionalProperties, rhs.additionalProperties).append(title, rhs.title).append(type, rhs.type).append(errors, rhs.errors).append(status, rhs.status).isEquals();
    }

}
