
package com.akamai.luna.papi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "propertyVersion",
    "network",
    "note",
    "notifyEmails",
    "acknowledgeWarnings",
    "acknowledgeAllWarnings",
    "activationType",
    "fastPush",
    "ignoreHttpErrors",
    "complianceRecord"
})
public class PropertyActivationRequest {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    private Integer propertyVersion;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("network")
    private PropertyActivationRequest.Network network;
    @JsonProperty("note")
    private String note;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifyEmails")
    private List<String> notifyEmails = new ArrayList<String>();
    @JsonProperty("acknowledgeWarnings")
    private List<String> acknowledgeWarnings = new ArrayList<String>();
    @JsonProperty("acknowledgeAllWarnings")
    private Boolean acknowledgeAllWarnings = false;
    @JsonProperty("activationType")
    private PropertyActivationRequest.ActivationType activationType = PropertyActivationRequest.ActivationType.fromValue("ACTIVATE");
    @JsonProperty("fastPush")
    private Boolean fastPush = true;
    @JsonProperty("ignoreHttpErrors")
    private Boolean ignoreHttpErrors = true;
    @JsonProperty("complianceRecord")
    private Object complianceRecord;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    public Integer getPropertyVersion() {
        return propertyVersion;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("propertyVersion")
    public void setPropertyVersion(Integer propertyVersion) {
        this.propertyVersion = propertyVersion;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("network")
    public PropertyActivationRequest.Network getNetwork() {
        return network;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("network")
    public void setNetwork(PropertyActivationRequest.Network network) {
        this.network = network;
    }

    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifyEmails")
    public List<String> getNotifyEmails() {
        return notifyEmails;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifyEmails")
    public void setNotifyEmails(List<String> notifyEmails) {
        this.notifyEmails = notifyEmails;
    }

    @JsonProperty("acknowledgeWarnings")
    public List<String> getAcknowledgeWarnings() {
        return acknowledgeWarnings;
    }

    @JsonProperty("acknowledgeWarnings")
    public void setAcknowledgeWarnings(List<String> acknowledgeWarnings) {
        this.acknowledgeWarnings = acknowledgeWarnings;
    }

    @JsonProperty("acknowledgeAllWarnings")
    public Boolean getAcknowledgeAllWarnings() {
        return acknowledgeAllWarnings;
    }

    @JsonProperty("acknowledgeAllWarnings")
    public void setAcknowledgeAllWarnings(Boolean acknowledgeAllWarnings) {
        this.acknowledgeAllWarnings = acknowledgeAllWarnings;
    }

    @JsonProperty("activationType")
    public PropertyActivationRequest.ActivationType getActivationType() {
        return activationType;
    }

    @JsonProperty("activationType")
    public void setActivationType(PropertyActivationRequest.ActivationType activationType) {
        this.activationType = activationType;
    }

    @JsonProperty("fastPush")
    public Boolean getFastPush() {
        return fastPush;
    }

    @JsonProperty("fastPush")
    public void setFastPush(Boolean fastPush) {
        this.fastPush = fastPush;
    }

    @JsonProperty("ignoreHttpErrors")
    public Boolean getIgnoreHttpErrors() {
        return ignoreHttpErrors;
    }

    @JsonProperty("ignoreHttpErrors")
    public void setIgnoreHttpErrors(Boolean ignoreHttpErrors) {
        this.ignoreHttpErrors = ignoreHttpErrors;
    }

    @JsonProperty("complianceRecord")
    public Object getComplianceRecord() {
        return complianceRecord;
    }

    @JsonProperty("complianceRecord")
    public void setComplianceRecord(Object complianceRecord) {
        this.complianceRecord = complianceRecord;
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
        return new ToStringBuilder(this).append("propertyVersion", propertyVersion).append("network", network).append("note", note).append("notifyEmails", notifyEmails).append("acknowledgeWarnings", acknowledgeWarnings).append("acknowledgeAllWarnings", acknowledgeAllWarnings).append("activationType", activationType).append("fastPush", fastPush).append("ignoreHttpErrors", ignoreHttpErrors).append("complianceRecord", complianceRecord).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(activationType).append(ignoreHttpErrors).append(note).append(complianceRecord).append(propertyVersion).append(acknowledgeAllWarnings).append(additionalProperties).append(fastPush).append(notifyEmails).append(network).append(acknowledgeWarnings).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PropertyActivationRequest) == false) {
            return false;
        }
        PropertyActivationRequest rhs = ((PropertyActivationRequest) other);
        return new EqualsBuilder().append(activationType, rhs.activationType).append(ignoreHttpErrors, rhs.ignoreHttpErrors).append(note, rhs.note).append(complianceRecord, rhs.complianceRecord).append(propertyVersion, rhs.propertyVersion).append(acknowledgeAllWarnings, rhs.acknowledgeAllWarnings).append(additionalProperties, rhs.additionalProperties).append(fastPush, rhs.fastPush).append(notifyEmails, rhs.notifyEmails).append(network, rhs.network).append(acknowledgeWarnings, rhs.acknowledgeWarnings).isEquals();
    }

    public enum ActivationType {

        ACTIVATE("ACTIVATE"),
        DEACTIVATE("DEACTIVATE");
        private final String value;
        private final static Map<String, PropertyActivationRequest.ActivationType> CONSTANTS = new HashMap<String, PropertyActivationRequest.ActivationType>();

        static {
            for (PropertyActivationRequest.ActivationType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ActivationType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static PropertyActivationRequest.ActivationType fromValue(String value) {
            PropertyActivationRequest.ActivationType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Network {

        STAGING("STAGING"),
        PRODUCTION("PRODUCTION");
        private final String value;
        private final static Map<String, PropertyActivationRequest.Network> CONSTANTS = new HashMap<String, PropertyActivationRequest.Network>();

        static {
            for (PropertyActivationRequest.Network c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Network(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static PropertyActivationRequest.Network fromValue(String value) {
            PropertyActivationRequest.Network constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
