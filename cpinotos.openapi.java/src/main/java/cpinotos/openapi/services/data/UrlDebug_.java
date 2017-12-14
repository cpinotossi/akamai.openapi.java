
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
 * Encapsulates various HTTP and DNS information for a URL in a top-level `urlDebug` member.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dnsInformation",
    "httpResponse",
    "logs",
    "responseHeaders"
})
public class UrlDebug_ {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dnsInformation")
    private List<String> dnsInformation = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("httpResponse")
    private List<HttpResponse> httpResponse = new ArrayList<HttpResponse>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    private List<Log_> logs = new ArrayList<Log_>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("responseHeaders")
    private List<String> responseHeaders = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dnsInformation")
    public List<String> getDnsInformation() {
        return dnsInformation;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dnsInformation")
    public void setDnsInformation(List<String> dnsInformation) {
        this.dnsInformation = dnsInformation;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("httpResponse")
    public List<HttpResponse> getHttpResponse() {
        return httpResponse;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("httpResponse")
    public void setHttpResponse(List<HttpResponse> httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public List<Log_> getLogs() {
        return logs;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public void setLogs(List<Log_> logs) {
        this.logs = logs;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("responseHeaders")
    public List<String> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("responseHeaders")
    public void setResponseHeaders(List<String> responseHeaders) {
        this.responseHeaders = responseHeaders;
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
        return new ToStringBuilder(this).append("dnsInformation", dnsInformation).append("httpResponse", httpResponse).append("logs", logs).append("responseHeaders", responseHeaders).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(responseHeaders).append(additionalProperties).append(httpResponse).append(logs).append(dnsInformation).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UrlDebug_) == false) {
            return false;
        }
        UrlDebug_ rhs = ((UrlDebug_) other);
        return new EqualsBuilder().append(responseHeaders, rhs.responseHeaders).append(additionalProperties, rhs.additionalProperties).append(httpResponse, rhs.httpResponse).append(logs, rhs.logs).append(dnsInformation, rhs.dnsInformation).isEquals();
    }

}
