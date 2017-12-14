
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
    "du-info",
    "directory",
    "required"
})
public class Du {

    @JsonProperty("du-info")
    private DuInfo duInfo;
    /**
     * directory
     * <p>
     * absolute Path of the Folder
     * 
     */
    @JsonProperty("directory")
    @JsonPropertyDescription("absolute Path of the Folder")
    private String directory = "";
    @JsonProperty("required")
    private Object required;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("du-info")
    public DuInfo getDuInfo() {
        return duInfo;
    }

    @JsonProperty("du-info")
    public void setDuInfo(DuInfo duInfo) {
        this.duInfo = duInfo;
    }

    /**
     * directory
     * <p>
     * absolute Path of the Folder
     * 
     */
    @JsonProperty("directory")
    public String getDirectory() {
        return directory;
    }

    /**
     * directory
     * <p>
     * absolute Path of the Folder
     * 
     */
    @JsonProperty("directory")
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @JsonProperty("required")
    public Object getRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(Object required) {
        this.required = required;
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
        return new ToStringBuilder(this).append("duInfo", duInfo).append("directory", directory).append("required", required).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(directory).append(required).append(duInfo).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Du) == false) {
            return false;
        }
        Du rhs = ((Du) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(directory, rhs.directory).append(required, rhs.required).append(duInfo, rhs.duInfo).isEquals();
    }

}
