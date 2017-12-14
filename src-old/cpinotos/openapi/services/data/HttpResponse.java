
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
 * Collects information about the HTTP response as a series of `name`/`value` pairs. See [TCP Errors](#tcperrors) for details on some of the error codes that appear in this data.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "value"
})
public class HttpResponse {

    /**
     * The HTTP response key.
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The HTTP response key.")
    private String name;
    /**
     * The HTTP response value.
     * (Required)
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The HTTP response value.")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The HTTP response key.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The HTTP response key.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The HTTP response value.
     * (Required)
     * 
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * The HTTP response value.
     * (Required)
     * 
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
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
        return new ToStringBuilder(this).append("name", name).append("value", value).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(additionalProperties).append(value).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HttpResponse) == false) {
            return false;
        }
        HttpResponse rhs = ((HttpResponse) other);
        return new EqualsBuilder().append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).append(value, rhs.value).isEquals();
    }

}
