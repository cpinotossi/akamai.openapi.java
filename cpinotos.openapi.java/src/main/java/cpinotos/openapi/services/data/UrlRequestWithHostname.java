
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
 * Purge Request Body
 * <p>
 * Specifies a list of server paths to invalidate or delete for a common `hostname`.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "objects",
    "hostname"
})
public class UrlRequestWithHostname {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objects")
    private List<String> objects = new ArrayList<String>();
    /**
     * Identifies the domain from which the content is purged.
     * 
     */
    @JsonProperty("hostname")
    @JsonPropertyDescription("Identifies the domain from which the content is purged.")
    private String hostname;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objects")
    public List<String> getObjects() {
        return objects;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objects")
    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    /**
     * Identifies the domain from which the content is purged.
     * 
     */
    @JsonProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    /**
     * Identifies the domain from which the content is purged.
     * 
     */
    @JsonProperty("hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
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
        return new ToStringBuilder(this).append("objects", objects).append("hostname", hostname).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hostname).append(additionalProperties).append(objects).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UrlRequestWithHostname) == false) {
            return false;
        }
        UrlRequestWithHostname rhs = ((UrlRequestWithHostname) other);
        return new EqualsBuilder().append(hostname, rhs.hostname).append(additionalProperties, rhs.additionalProperties).append(objects, rhs.objects).isEquals();
    }

}
