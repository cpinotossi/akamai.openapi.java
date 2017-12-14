
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
 * PAPI CPCode get List result.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "accountId",
    "contractId",
    "groupId",
    "cpcodes"
})
public class ListCPCodeResult {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("accountId")
    private String accountId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("contractId")
    private String contractId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("groupId")
    private String groupId;
    /**
     * PAPI CPCode get List result.
     * (Required)
     * 
     */
    @JsonProperty("cpcodes")
    @JsonPropertyDescription("PAPI CPCode get List result.")
    private Cpcodes cpcodes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("contractId")
    public String getContractId() {
        return contractId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("contractId")
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * PAPI CPCode get List result.
     * (Required)
     * 
     */
    @JsonProperty("cpcodes")
    public Cpcodes getCpcodes() {
        return cpcodes;
    }

    /**
     * PAPI CPCode get List result.
     * (Required)
     * 
     */
    @JsonProperty("cpcodes")
    public void setCpcodes(Cpcodes cpcodes) {
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
        return new ToStringBuilder(this).append("accountId", accountId).append("contractId", contractId).append("groupId", groupId).append("cpcodes", cpcodes).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(contractId).append(accountId).append(additionalProperties).append(groupId).append(cpcodes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ListCPCodeResult) == false) {
            return false;
        }
        ListCPCodeResult rhs = ((ListCPCodeResult) other);
        return new EqualsBuilder().append(contractId, rhs.contractId).append(accountId, rhs.accountId).append(additionalProperties, rhs.additionalProperties).append(groupId, rhs.groupId).append(cpcodes, rhs.cpcodes).isEquals();
    }

}
