
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
 * PAPI create CPCode.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpcodeLink",
    "contractId",
    "groupId",
    "cpcodes"
})
public class CreateNewCPCodeResultV0 {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeLink")
    private String cpcodeLink;
    @JsonProperty("contractId")
    private String contractId;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("cpcodes")
    private String cpcodes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeLink")
    public String getCpcodeLink() {
        return cpcodeLink;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpcodeLink")
    public void setCpcodeLink(String cpcodeLink) {
        this.cpcodeLink = cpcodeLink;
    }

    @JsonProperty("contractId")
    public String getContractId() {
        return contractId;
    }

    @JsonProperty("contractId")
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("cpcodes")
    public String getCpcodes() {
        return cpcodes;
    }

    @JsonProperty("cpcodes")
    public void setCpcodes(String cpcodes) {
        this.cpcodes = cpcodes;
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
        return new ToStringBuilder(this).append("cpcodeLink", cpcodeLink).append("contractId", contractId).append("groupId", groupId).append("cpcodes", cpcodes).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(contractId).append(cpcodeLink).append(additionalProperties).append(groupId).append(cpcodes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreateNewCPCodeResultV0) == false) {
            return false;
        }
        CreateNewCPCodeResultV0 rhs = ((CreateNewCPCodeResultV0) other);
        return new EqualsBuilder().append(contractId, rhs.contractId).append(cpcodeLink, rhs.cpcodeLink).append(additionalProperties, rhs.additionalProperties).append(groupId, rhs.groupId).append(cpcodes, rhs.cpcodes).isEquals();
    }

}
