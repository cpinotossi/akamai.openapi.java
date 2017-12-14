
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
 * Outer wrapper for a URL debug response object.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "urlDebug"
})
public class UrlDebug {

    /**
     * Encapsulates various HTTP and DNS information for a URL in a top-level `urlDebug` member.
     * (Required)
     * 
     */
    @JsonProperty("urlDebug")
    @JsonPropertyDescription("Encapsulates various HTTP and DNS information for a URL in a top-level `urlDebug` member.")
    private UrlDebug_ urlDebug;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Encapsulates various HTTP and DNS information for a URL in a top-level `urlDebug` member.
     * (Required)
     * 
     */
    @JsonProperty("urlDebug")
    public UrlDebug_ getUrlDebug() {
        return urlDebug;
    }

    /**
     * Encapsulates various HTTP and DNS information for a URL in a top-level `urlDebug` member.
     * (Required)
     * 
     */
    @JsonProperty("urlDebug")
    public void setUrlDebug(UrlDebug_ urlDebug) {
        this.urlDebug = urlDebug;
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
        return new ToStringBuilder(this).append("urlDebug", urlDebug).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(urlDebug).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UrlDebug) == false) {
            return false;
        }
        UrlDebug rhs = ((UrlDebug) other);
        return new EqualsBuilder().append(urlDebug, rhs.urlDebug).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
