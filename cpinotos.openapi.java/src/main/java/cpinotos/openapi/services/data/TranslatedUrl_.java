
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
 * Encapsulates basic metadata about an Akamaized URL (ARL).
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpCode",
    "originServer",
    "serialNumber",
    "ttl",
    "typeCode"
})
public class TranslatedUrl_ {

    /**
     * The CP Code that applies to this URL.
     * (Required)
     * 
     */
    @JsonProperty("cpCode")
    @JsonPropertyDescription("The CP Code that applies to this URL.")
    private Integer cpCode;
    /**
     * Origin hostname from which this URL derived.
     * (Required)
     * 
     */
    @JsonProperty("originServer")
    @JsonPropertyDescription("Origin hostname from which this URL derived.")
    private String originServer;
    /**
     * A unique identifier for a server mapped to a region.
     * (Required)
     * 
     */
    @JsonProperty("serialNumber")
    @JsonPropertyDescription("A unique identifier for a server mapped to a region.")
    private Integer serialNumber;
    /**
     * Remaining time to live in cache, either the number of seconds or `Infinite`.
     * (Required)
     * 
     */
    @JsonProperty("ttl")
    @JsonPropertyDescription("Remaining time to live in cache, either the number of seconds or `Infinite`.")
    private String ttl;
    /**
     * Describes how the server is configured.
     * (Required)
     * 
     */
    @JsonProperty("typeCode")
    @JsonPropertyDescription("Describes how the server is configured.")
    private String typeCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The CP Code that applies to this URL.
     * (Required)
     * 
     */
    @JsonProperty("cpCode")
    public Integer getCpCode() {
        return cpCode;
    }

    /**
     * The CP Code that applies to this URL.
     * (Required)
     * 
     */
    @JsonProperty("cpCode")
    public void setCpCode(Integer cpCode) {
        this.cpCode = cpCode;
    }

    /**
     * Origin hostname from which this URL derived.
     * (Required)
     * 
     */
    @JsonProperty("originServer")
    public String getOriginServer() {
        return originServer;
    }

    /**
     * Origin hostname from which this URL derived.
     * (Required)
     * 
     */
    @JsonProperty("originServer")
    public void setOriginServer(String originServer) {
        this.originServer = originServer;
    }

    /**
     * A unique identifier for a server mapped to a region.
     * (Required)
     * 
     */
    @JsonProperty("serialNumber")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    /**
     * A unique identifier for a server mapped to a region.
     * (Required)
     * 
     */
    @JsonProperty("serialNumber")
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Remaining time to live in cache, either the number of seconds or `Infinite`.
     * (Required)
     * 
     */
    @JsonProperty("ttl")
    public String getTtl() {
        return ttl;
    }

    /**
     * Remaining time to live in cache, either the number of seconds or `Infinite`.
     * (Required)
     * 
     */
    @JsonProperty("ttl")
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    /**
     * Describes how the server is configured.
     * (Required)
     * 
     */
    @JsonProperty("typeCode")
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * Describes how the server is configured.
     * (Required)
     * 
     */
    @JsonProperty("typeCode")
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
        return new ToStringBuilder(this).append("cpCode", cpCode).append("originServer", originServer).append("serialNumber", serialNumber).append("ttl", ttl).append("typeCode", typeCode).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cpCode).append(serialNumber).append(additionalProperties).append(ttl).append(originServer).append(typeCode).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TranslatedUrl_) == false) {
            return false;
        }
        TranslatedUrl_ rhs = ((TranslatedUrl_) other);
        return new EqualsBuilder().append(cpCode, rhs.cpCode).append(serialNumber, rhs.serialNumber).append(additionalProperties, rhs.additionalProperties).append(ttl, rhs.ttl).append(originServer, rhs.originServer).append(typeCode, rhs.typeCode).isEquals();
    }

}
