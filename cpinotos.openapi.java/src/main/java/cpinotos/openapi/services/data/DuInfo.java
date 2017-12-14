
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bytes",
    "files"
})
public class DuInfo {

    /**
     * bytes
     * <p>
     * Byte size of the folder.
     * (Required)
     * 
     */
    @JsonProperty("bytes")
    @JsonPropertyDescription("Byte size of the folder.")
    private Integer bytes = 0;
    /**
     * files
     * <p>
     * Number of files inside the folder.
     * (Required)
     * 
     */
    @JsonProperty("files")
    @JsonPropertyDescription("Number of files inside the folder.")
    private Integer files = 0;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * bytes
     * <p>
     * Byte size of the folder.
     * (Required)
     * 
     */
    @JsonProperty("bytes")
    public Integer getBytes() {
        return bytes;
    }

    /**
     * bytes
     * <p>
     * Byte size of the folder.
     * (Required)
     * 
     */
    @JsonProperty("bytes")
    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    /**
     * files
     * <p>
     * Number of files inside the folder.
     * (Required)
     * 
     */
    @JsonProperty("files")
    public Integer getFiles() {
        return files;
    }

    /**
     * files
     * <p>
     * Number of files inside the folder.
     * (Required)
     * 
     */
    @JsonProperty("files")
    public void setFiles(Integer files) {
        this.files = files;
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
        return new ToStringBuilder(this).append("bytes", bytes).append("files", files).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(files).append(additionalProperties).append(bytes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DuInfo) == false) {
            return false;
        }
        DuInfo rhs = ((DuInfo) other);
        return new EqualsBuilder().append(files, rhs.files).append(additionalProperties, rhs.additionalProperties).append(bytes, rhs.bytes).isEquals();
    }

}
