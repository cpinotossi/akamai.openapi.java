
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
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Represents a URL Debugger request body.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "edgeIp",
    "headers",
    "url"
})
public class UrlDebugPostBody {

    /**
     * The IP address of the edge server associated with this request.
     * 
     */
    @JsonProperty("edgeIp")
    @JsonPropertyDescription("The IP address of the edge server associated with this request.")
    private String edgeIp;
    @JsonProperty("headers")
    private List<String> headers = new ArrayList<String>();
    /**
     * The URL to debug.
     * (Required)
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The URL to debug.")
    private String url;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The IP address of the edge server associated with this request.
     * 
     */
    @JsonProperty("edgeIp")
    public String getEdgeIp() {
        return edgeIp;
    }

    /**
     * The IP address of the edge server associated with this request.
     * 
     */
    @JsonProperty("edgeIp")
    public void setEdgeIp(String edgeIp) {
        this.edgeIp = edgeIp;
    }

    @JsonProperty("headers")
    public List<String> getHeaders() {
        return headers;
    }

    @JsonProperty("headers")
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    /**
     * The URL to debug.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The URL to debug.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
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
        return new ToStringBuilder(this).append("edgeIp", edgeIp).append("headers", headers).append("url", url).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(headers).append(additionalProperties).append(edgeIp).append(url).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UrlDebugPostBody) == false) {
            return false;
        }
        UrlDebugPostBody rhs = ((UrlDebugPostBody) other);
        return new EqualsBuilder().append(headers, rhs.headers).append(additionalProperties, rhs.additionalProperties).append(edgeIp, rhs.edgeIp).append(url, rhs.url).isEquals();
    }

}
