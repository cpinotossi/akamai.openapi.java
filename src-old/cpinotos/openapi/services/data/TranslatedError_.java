
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
 * Encapsulates an error translation within a top-level `translatedError` member.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "clientIp",
    "connectingIp",
    "cpcode",
    "epochTime",
    "httpResponseCode",
    "logs",
    "originHostname",
    "originIp",
    "reasonForFailure",
    "requestMethod",
    "serverIp",
    "timestamp",
    "url",
    "userAgent",
    "wafDetails"
})
public class TranslatedError_ {

    /**
     * The IP address of the client that attempted to contact the edge server to request the content. This is the machine that made a TCP connection to the edge server, possibly the IP address of a proxy server.
     * (Required)
     * 
     */
    @JsonProperty("clientIp")
    @JsonPropertyDescription("The IP address of the client that attempted to contact the edge server to request the content. This is the machine that made a TCP connection to the edge server, possibly the IP address of a proxy server.")
    private String clientIp;
    /**
     * The IP address of the connecting server.
     * (Required)
     * 
     */
    @JsonProperty("connectingIp")
    @JsonPropertyDescription("The IP address of the connecting server.")
    private String connectingIp;
    /**
     * The CP code for this request.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    @JsonPropertyDescription("The CP code for this request.")
    private String cpcode;
    /**
     * When the request occurred, in epoch seconds. See also the `timestamp`.
     * (Required)
     * 
     */
    @JsonProperty("epochTime")
    @JsonPropertyDescription("When the request occurred, in epoch seconds. See also the `timestamp`.")
    private Integer epochTime;
    /**
     * The HTTP response code.
     * (Required)
     * 
     */
    @JsonProperty("httpResponseCode")
    @JsonPropertyDescription("The HTTP response code.")
    private Integer httpResponseCode;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    private List<Log> logs = new ArrayList<Log>();
    /**
     * The origin hostname.
     * (Required)
     * 
     */
    @JsonProperty("originHostname")
    @JsonPropertyDescription("The origin hostname.")
    private String originHostname;
    /**
     * The origin IP address that the edge server attempted to connect to.
     * (Required)
     * 
     */
    @JsonProperty("originIp")
    @JsonPropertyDescription("The origin IP address that the edge server attempted to connect to.")
    private String originIp;
    /**
     * An error string captured on the edge server that explains why this transaction failed. See [Error Codes](data.html#errorcodes) for details.
     * (Required)
     * 
     */
    @JsonProperty("reasonForFailure")
    @JsonPropertyDescription("An error string captured on the edge server that explains why this transaction failed. See [Error Codes](data.html#errorcodes) for details.")
    private String reasonForFailure;
    /**
     * The HTTP method that the client requested for the URL.
     * (Required)
     * 
     */
    @JsonProperty("requestMethod")
    @JsonPropertyDescription("The HTTP method that the client requested for the URL.")
    private String requestMethod;
    /**
     * The IP address of the edge server that answered the client's request or served the error.
     * (Required)
     * 
     */
    @JsonProperty("serverIp")
    @JsonPropertyDescription("The IP address of the edge server that answered the client's request or served the error.")
    private String serverIp;
    /**
     * When the request occurred, formatted as RFC-2616. See also the `epochTime`.
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("When the request occurred, formatted as RFC-2616. See also the `epochTime`.")
    private String timestamp;
    /**
     * The URL address the client tried to access.
     * (Required)
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The URL address the client tried to access.")
    private String url;
    /**
     * The `User-Agent` HTTP header sent by the client to the edge server. A value of `N/A` means that the client did not identify itself.
     * (Required)
     * 
     */
    @JsonProperty("userAgent")
    @JsonPropertyDescription("The `User-Agent` HTTP header sent by the client to the edge server. A value of `N/A` means that the client did not identify itself.")
    private String userAgent;
    /**
     * Provides details on any firewall applied to this request.
     * (Required)
     * 
     */
    @JsonProperty("wafDetails")
    @JsonPropertyDescription("Provides details on any firewall applied to this request.")
    private String wafDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The IP address of the client that attempted to contact the edge server to request the content. This is the machine that made a TCP connection to the edge server, possibly the IP address of a proxy server.
     * (Required)
     * 
     */
    @JsonProperty("clientIp")
    public String getClientIp() {
        return clientIp;
    }

    /**
     * The IP address of the client that attempted to contact the edge server to request the content. This is the machine that made a TCP connection to the edge server, possibly the IP address of a proxy server.
     * (Required)
     * 
     */
    @JsonProperty("clientIp")
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * The IP address of the connecting server.
     * (Required)
     * 
     */
    @JsonProperty("connectingIp")
    public String getConnectingIp() {
        return connectingIp;
    }

    /**
     * The IP address of the connecting server.
     * (Required)
     * 
     */
    @JsonProperty("connectingIp")
    public void setConnectingIp(String connectingIp) {
        this.connectingIp = connectingIp;
    }

    /**
     * The CP code for this request.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    public String getCpcode() {
        return cpcode;
    }

    /**
     * The CP code for this request.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    public void setCpcode(String cpcode) {
        this.cpcode = cpcode;
    }

    /**
     * When the request occurred, in epoch seconds. See also the `timestamp`.
     * (Required)
     * 
     */
    @JsonProperty("epochTime")
    public Integer getEpochTime() {
        return epochTime;
    }

    /**
     * When the request occurred, in epoch seconds. See also the `timestamp`.
     * (Required)
     * 
     */
    @JsonProperty("epochTime")
    public void setEpochTime(Integer epochTime) {
        this.epochTime = epochTime;
    }

    /**
     * The HTTP response code.
     * (Required)
     * 
     */
    @JsonProperty("httpResponseCode")
    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    /**
     * The HTTP response code.
     * (Required)
     * 
     */
    @JsonProperty("httpResponseCode")
    public void setHttpResponseCode(Integer httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public List<Log> getLogs() {
        return logs;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    /**
     * The origin hostname.
     * (Required)
     * 
     */
    @JsonProperty("originHostname")
    public String getOriginHostname() {
        return originHostname;
    }

    /**
     * The origin hostname.
     * (Required)
     * 
     */
    @JsonProperty("originHostname")
    public void setOriginHostname(String originHostname) {
        this.originHostname = originHostname;
    }

    /**
     * The origin IP address that the edge server attempted to connect to.
     * (Required)
     * 
     */
    @JsonProperty("originIp")
    public String getOriginIp() {
        return originIp;
    }

    /**
     * The origin IP address that the edge server attempted to connect to.
     * (Required)
     * 
     */
    @JsonProperty("originIp")
    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    /**
     * An error string captured on the edge server that explains why this transaction failed. See [Error Codes](data.html#errorcodes) for details.
     * (Required)
     * 
     */
    @JsonProperty("reasonForFailure")
    public String getReasonForFailure() {
        return reasonForFailure;
    }

    /**
     * An error string captured on the edge server that explains why this transaction failed. See [Error Codes](data.html#errorcodes) for details.
     * (Required)
     * 
     */
    @JsonProperty("reasonForFailure")
    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    /**
     * The HTTP method that the client requested for the URL.
     * (Required)
     * 
     */
    @JsonProperty("requestMethod")
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * The HTTP method that the client requested for the URL.
     * (Required)
     * 
     */
    @JsonProperty("requestMethod")
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * The IP address of the edge server that answered the client's request or served the error.
     * (Required)
     * 
     */
    @JsonProperty("serverIp")
    public String getServerIp() {
        return serverIp;
    }

    /**
     * The IP address of the edge server that answered the client's request or served the error.
     * (Required)
     * 
     */
    @JsonProperty("serverIp")
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    /**
     * When the request occurred, formatted as RFC-2616. See also the `epochTime`.
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * When the request occurred, formatted as RFC-2616. See also the `epochTime`.
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * The URL address the client tried to access.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The URL address the client tried to access.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * The `User-Agent` HTTP header sent by the client to the edge server. A value of `N/A` means that the client did not identify itself.
     * (Required)
     * 
     */
    @JsonProperty("userAgent")
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * The `User-Agent` HTTP header sent by the client to the edge server. A value of `N/A` means that the client did not identify itself.
     * (Required)
     * 
     */
    @JsonProperty("userAgent")
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Provides details on any firewall applied to this request.
     * (Required)
     * 
     */
    @JsonProperty("wafDetails")
    public String getWafDetails() {
        return wafDetails;
    }

    /**
     * Provides details on any firewall applied to this request.
     * (Required)
     * 
     */
    @JsonProperty("wafDetails")
    public void setWafDetails(String wafDetails) {
        this.wafDetails = wafDetails;
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
        return new ToStringBuilder(this).append("clientIp", clientIp).append("connectingIp", connectingIp).append("cpcode", cpcode).append("epochTime", epochTime).append("httpResponseCode", httpResponseCode).append("logs", logs).append("originHostname", originHostname).append("originIp", originIp).append("reasonForFailure", reasonForFailure).append("requestMethod", requestMethod).append("serverIp", serverIp).append("timestamp", timestamp).append("url", url).append("userAgent", userAgent).append("wafDetails", wafDetails).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(connectingIp).append(requestMethod).append(userAgent).append(url).append(cpcode).append(clientIp).append(reasonForFailure).append(serverIp).append(httpResponseCode).append(additionalProperties).append(logs).append(wafDetails).append(epochTime).append(originHostname).append(originIp).append(timestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TranslatedError_) == false) {
            return false;
        }
        TranslatedError_ rhs = ((TranslatedError_) other);
        return new EqualsBuilder().append(connectingIp, rhs.connectingIp).append(requestMethod, rhs.requestMethod).append(userAgent, rhs.userAgent).append(url, rhs.url).append(cpcode, rhs.cpcode).append(clientIp, rhs.clientIp).append(reasonForFailure, rhs.reasonForFailure).append(serverIp, rhs.serverIp).append(httpResponseCode, rhs.httpResponseCode).append(additionalProperties, rhs.additionalProperties).append(logs, rhs.logs).append(wafDetails, rhs.wafDetails).append(epochTime, rhs.epochTime).append(originHostname, rhs.originHostname).append(originIp, rhs.originIp).append(timestamp, rhs.timestamp).isEquals();
    }

}
