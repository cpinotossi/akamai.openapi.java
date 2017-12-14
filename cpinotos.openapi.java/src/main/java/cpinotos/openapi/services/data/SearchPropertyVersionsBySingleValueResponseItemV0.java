
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
 * PAPI Search Result Item.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "updatedByUser",
    "stagingStatus",
    "assetId",
    "propertyName",
    "propertyVersion",
    "updatedDate",
    "contractId",
    "accountId",
    "groupId",
    "propertyId",
    "productionStatus"
})
public class SearchPropertyVersionsBySingleValueResponseItemV0 {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedByUser")
    private String updatedByUser;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stagingStatus")
    private String stagingStatus;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("assetId")
    private String assetId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyName")
    private String propertyName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    private Integer propertyVersion;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedDate")
    private String updatedDate;
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
    @JsonProperty("accountId")
    private String accountId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("groupId")
    private String groupId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyId")
    private String propertyId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productionStatus")
    private String productionStatus;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedByUser")
    public String getUpdatedByUser() {
        return updatedByUser;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedByUser")
    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stagingStatus")
    public String getStagingStatus() {
        return stagingStatus;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stagingStatus")
    public void setStagingStatus(String stagingStatus) {
        this.stagingStatus = stagingStatus;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("assetId")
    public String getAssetId() {
        return assetId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("assetId")
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyName")
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyName")
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    public Integer getPropertyVersion() {
        return propertyVersion;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    public void setPropertyVersion(Integer propertyVersion) {
        this.propertyVersion = propertyVersion;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedDate")
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedDate")
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
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
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyId")
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyId")
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productionStatus")
    public String getProductionStatus() {
        return productionStatus;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productionStatus")
    public void setProductionStatus(String productionStatus) {
        this.productionStatus = productionStatus;
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
        return new ToStringBuilder(this).append("updatedByUser", updatedByUser).append("stagingStatus", stagingStatus).append("assetId", assetId).append("propertyName", propertyName).append("propertyVersion", propertyVersion).append("updatedDate", updatedDate).append("contractId", contractId).append("accountId", accountId).append("groupId", groupId).append("propertyId", propertyId).append("productionStatus", productionStatus).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(updatedByUser).append(groupId).append(updatedDate).append(accountId).append(propertyVersion).append(propertyName).append(assetId).append(productionStatus).append(contractId).append(stagingStatus).append(additionalProperties).append(propertyId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchPropertyVersionsBySingleValueResponseItemV0) == false) {
            return false;
        }
        SearchPropertyVersionsBySingleValueResponseItemV0 rhs = ((SearchPropertyVersionsBySingleValueResponseItemV0) other);
        return new EqualsBuilder().append(updatedByUser, rhs.updatedByUser).append(groupId, rhs.groupId).append(updatedDate, rhs.updatedDate).append(accountId, rhs.accountId).append(propertyVersion, rhs.propertyVersion).append(propertyName, rhs.propertyName).append(assetId, rhs.assetId).append(productionStatus, rhs.productionStatus).append(contractId, rhs.contractId).append(stagingStatus, rhs.stagingStatus).append(additionalProperties, rhs.additionalProperties).append(propertyId, rhs.propertyId).isEquals();
    }

}
