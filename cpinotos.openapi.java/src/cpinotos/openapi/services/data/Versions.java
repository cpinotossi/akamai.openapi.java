
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
    "items"
})
public class Versions {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("items")
    private List<SearchPropertyVersionsBySingleValueResponseItemV0> items = new ArrayList<SearchPropertyVersionsBySingleValueResponseItemV0>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("items")
    public List<SearchPropertyVersionsBySingleValueResponseItemV0> getItems() {
        return items;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("items")
    public void setItems(List<SearchPropertyVersionsBySingleValueResponseItemV0> items) {
        this.items = items;
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
        return new ToStringBuilder(this).append("items", items).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(items).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Versions) == false) {
            return false;
        }
        Versions rhs = ((Versions) other);
        return new EqualsBuilder().append(items, rhs.items).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
