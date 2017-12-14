
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
 * PAPI CPCode Object
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpcodeId",
    "cpcodeName",
    "productIds",
    "createdDate"
})
public class ListCPCode {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeId")
    private String cpcodeId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeName")
    private String cpcodeName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productIds")
    private List<Object> productIds = new ArrayList<Object>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeId")
    public String getCpcodeId() {
        return cpcodeId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeId")
    public void setCpcodeId(String cpcodeId) {
        this.cpcodeId = cpcodeId;
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

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productIds")
    public List<Object> getProductIds() {
        return productIds;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productIds")
    public void setProductIds(List<Object> productIds) {
        this.productIds = productIds;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
        return new ToStringBuilder(this).append("cpcodeId", cpcodeId).append("cpcodeName", cpcodeName).append("productIds", productIds).append("createdDate", createdDate).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(createdDate).append(additionalProperties).append(cpcodeId).append(cpcodeName).append(productIds).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ListCPCode) == false) {
            return false;
        }
        ListCPCode rhs = ((ListCPCode) other);
        return new EqualsBuilder().append(createdDate, rhs.createdDate).append(additionalProperties, rhs.additionalProperties).append(cpcodeId, rhs.cpcodeId).append(cpcodeName, rhs.cpcodeName).append(productIds, rhs.productIds).isEquals();
    }

}
