
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
 * PAPI contract list result.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "accountId",
    "contractId",
    "products"
})
public class ProductsResult {

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
     * PAPI contract list, result list.
     * (Required)
     * 
     */
    @JsonProperty("products")
    @JsonPropertyDescription("PAPI contract list, result list.")
    private Products products;
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
     * PAPI contract list, result list.
     * (Required)
     * 
     */
    @JsonProperty("products")
    public Products getProducts() {
        return products;
    }

    /**
     * PAPI contract list, result list.
     * (Required)
     * 
     */
    @JsonProperty("products")
    public void setProducts(Products products) {
        this.products = products;
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
        return new ToStringBuilder(this).append("accountId", accountId).append("contractId", contractId).append("products", products).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(contractId).append(accountId).append(additionalProperties).append(products).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProductsResult) == false) {
            return false;
        }
        ProductsResult rhs = ((ProductsResult) other);
        return new EqualsBuilder().append(contractId, rhs.contractId).append(accountId, rhs.accountId).append(additionalProperties, rhs.additionalProperties).append(products, rhs.products).isEquals();
    }

}
