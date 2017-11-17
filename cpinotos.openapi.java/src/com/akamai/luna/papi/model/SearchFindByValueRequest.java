
package com.akamai.luna.papi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * A query to search currently active properties. Only one query member is allowed in the POST request.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "propertyName",
    "hostname",
    "edgeHostname"
})
public class SearchFindByValueRequest {

    /**
     * Search for properties by name.
     * 
     */
    @JsonProperty("propertyName")
    @JsonPropertyDescription("Search for properties by name.")
    private String propertyName;
    /**
     * Search for property versions active on a specific hostname.
     * 
     */
    @JsonProperty("hostname")
    @JsonPropertyDescription("Search for property versions active on a specific hostname.")
    private String hostname;
    /**
     * Search for property versions active on a specific edge hostname.
     * 
     */
    @JsonProperty("edgeHostname")
    @JsonPropertyDescription("Search for property versions active on a specific edge hostname.")
    private String edgeHostname;

    /**
     * Search for properties by name.
     * 
     */
    @JsonProperty("propertyName")
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Search for properties by name.
     * 
     */
    @JsonProperty("propertyName")
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Search for property versions active on a specific hostname.
     * 
     */
    @JsonProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    /**
     * Search for property versions active on a specific hostname.
     * 
     */
    @JsonProperty("hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Search for property versions active on a specific edge hostname.
     * 
     */
    @JsonProperty("edgeHostname")
    public String getEdgeHostname() {
        return edgeHostname;
    }

    /**
     * Search for property versions active on a specific edge hostname.
     * 
     */
    @JsonProperty("edgeHostname")
    public void setEdgeHostname(String edgeHostname) {
        this.edgeHostname = edgeHostname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("propertyName", propertyName).append("hostname", hostname).append("edgeHostname", edgeHostname).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hostname).append(edgeHostname).append(propertyName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchFindByValueRequest) == false) {
            return false;
        }
        SearchFindByValueRequest rhs = ((SearchFindByValueRequest) other);
        return new EqualsBuilder().append(hostname, rhs.hostname).append(edgeHostname, rhs.edgeHostname).append(propertyName, rhs.propertyName).isEquals();
    }

}
