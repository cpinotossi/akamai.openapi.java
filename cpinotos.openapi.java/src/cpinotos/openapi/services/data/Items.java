
package cpinotos.openapi.services.data;

import java.util.HashMap;
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
 * PAPI contract list, result item.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "productName",
    "productId"
})
public class Items {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productName")
    private String productName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productId")
    private String productId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productName")
    public String getProductName() {
        return productName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productName")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
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
        return new ToStringBuilder(this).append("productName", productName).append("productId", productId).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(productId).append(productName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Items) == false) {
            return false;
        }
        Items rhs = ((Items) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(productId, rhs.productId).append(productName, rhs.productName).isEquals();
    }

}
