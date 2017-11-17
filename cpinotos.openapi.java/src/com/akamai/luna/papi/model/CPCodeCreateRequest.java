
package com.akamai.luna.papi.model;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "productId",
    "cpcodeName"
})
public class CPCodeCreateRequest {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productId")
    private String productId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeName")
    private String cpcodeName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeName")
    public String getCpcodeName() {
        return cpcodeName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeName")
    public void setCpcodeName(String cpcodeName) {
        this.cpcodeName = cpcodeName;
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
        return new ToStringBuilder(this).append("productId", productId).append("cpcodeName", cpcodeName).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(productId).append(cpcodeName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CPCodeCreateRequest) == false) {
            return false;
        }
        CPCodeCreateRequest rhs = ((CPCodeCreateRequest) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(productId, rhs.productId).append(cpcodeName, rhs.cpcodeName).isEquals();
    }

}
