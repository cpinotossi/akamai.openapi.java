
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
 * Outer wrapper for Grep data.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "logLines"
})
public class LogLines {

    /**
     * Contains `grep` data within a top-level `logLines` member, arranged as tables of tab-delimited text. Note that this log data is arranged in a different format as in the [TranslatedError.logs[n]](data.html#20849ee) object.
     * (Required)
     * 
     */
    @JsonProperty("logLines")
    @JsonPropertyDescription("Contains `grep` data within a top-level `logLines` member, arranged as tables of tab-delimited text. Note that this log data is arranged in a different format as in the [TranslatedError.logs[n]](data.html#20849ee) object.")
    private LogLines_ logLines;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Contains `grep` data within a top-level `logLines` member, arranged as tables of tab-delimited text. Note that this log data is arranged in a different format as in the [TranslatedError.logs[n]](data.html#20849ee) object.
     * (Required)
     * 
     */
    @JsonProperty("logLines")
    public LogLines_ getLogLines() {
        return logLines;
    }

    /**
     * Contains `grep` data within a top-level `logLines` member, arranged as tables of tab-delimited text. Note that this log data is arranged in a different format as in the [TranslatedError.logs[n]](data.html#20849ee) object.
     * (Required)
     * 
     */
    @JsonProperty("logLines")
    public void setLogLines(LogLines_ logLines) {
        this.logLines = logLines;
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
        return new ToStringBuilder(this).append("logLines", logLines).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(logLines).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LogLines) == false) {
            return false;
        }
        LogLines rhs = ((LogLines) other);
        return new EqualsBuilder().append(logLines, rhs.logLines).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
