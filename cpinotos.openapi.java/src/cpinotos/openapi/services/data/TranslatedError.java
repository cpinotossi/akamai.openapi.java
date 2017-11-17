
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
 * Outer wrapper for a translated error message.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "translatedError"
})
public class TranslatedError {

    /**
     * Encapsulates an error translation within a top-level `translatedError` member.
     * (Required)
     * 
     */
    @JsonProperty("translatedError")
    @JsonPropertyDescription("Encapsulates an error translation within a top-level `translatedError` member.")
    private TranslatedError_ translatedError;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Encapsulates an error translation within a top-level `translatedError` member.
     * (Required)
     * 
     */
    @JsonProperty("translatedError")
    public TranslatedError_ getTranslatedError() {
        return translatedError;
    }

    /**
     * Encapsulates an error translation within a top-level `translatedError` member.
     * (Required)
     * 
     */
    @JsonProperty("translatedError")
    public void setTranslatedError(TranslatedError_ translatedError) {
        this.translatedError = translatedError;
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
        return new ToStringBuilder(this).append("translatedError", translatedError).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(translatedError).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TranslatedError) == false) {
            return false;
        }
        TranslatedError rhs = ((TranslatedError) other);
        return new EqualsBuilder().append(translatedError, rhs.translatedError).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
