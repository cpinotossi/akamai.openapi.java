
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
 * Contains `grep` data within a top-level `logLines` member, arranged as tables of tab-delimited text. Note that this log data is arranged in a different format as in the [TranslatedError.logs[n]](data.html#20849ee) object.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "headers",
    "logs"
})
public class LogLines_ {

    /**
     * Tab-delimited labels for each column of `logs` data.
     * (Required)
     * 
     */
    @JsonProperty("headers")
    @JsonPropertyDescription("Tab-delimited labels for each column of `logs` data.")
    private String headers;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    private List<String> logs = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Tab-delimited labels for each column of `logs` data.
     * (Required)
     * 
     */
    @JsonProperty("headers")
    public String getHeaders() {
        return headers;
    }

    /**
     * Tab-delimited labels for each column of `logs` data.
     * (Required)
     * 
     */
    @JsonProperty("headers")
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public List<String> getLogs() {
        return logs;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logs")
    public void setLogs(List<String> logs) {
        this.logs = logs;
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
        return new ToStringBuilder(this).append("headers", headers).append("logs", logs).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(headers).append(additionalProperties).append(logs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LogLines_) == false) {
            return false;
        }
        LogLines_ rhs = ((LogLines_) other);
        return new EqualsBuilder().append(headers, rhs.headers).append(additionalProperties, rhs.additionalProperties).append(logs, rhs.logs).isEquals();
    }

}
