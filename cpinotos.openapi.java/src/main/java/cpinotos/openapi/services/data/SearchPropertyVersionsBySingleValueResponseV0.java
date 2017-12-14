
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
 * PAPI Search Result.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "versions"
})
public class SearchPropertyVersionsBySingleValueResponseV0 {

    /**
     * PAPI Search Result.
     * (Required)
     * 
     */
    @JsonProperty("versions")
    @JsonPropertyDescription("PAPI Search Result.")
    private Versions versions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * PAPI Search Result.
     * (Required)
     * 
     */
    @JsonProperty("versions")
    public Versions getVersions() {
        return versions;
    }

    /**
     * PAPI Search Result.
     * (Required)
     * 
     */
    @JsonProperty("versions")
    public void setVersions(Versions versions) {
        this.versions = versions;
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
        return new ToStringBuilder(this).append("versions", versions).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(versions).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchPropertyVersionsBySingleValueResponseV0) == false) {
            return false;
        }
        SearchPropertyVersionsBySingleValueResponseV0 rhs = ((SearchPropertyVersionsBySingleValueResponseV0) other);
        return new EqualsBuilder().append(versions, rhs.versions).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
