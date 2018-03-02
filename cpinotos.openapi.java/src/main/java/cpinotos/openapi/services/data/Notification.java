
package cpinotos.openapi.services.data;

import java.util.Date;
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
 * Encapsulates each download notification.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpcode",
    "downloadTime",
    "uid",
    "host",
    "path"
})
public class Notification {

    /**
     * The content provider code under which traffic is billed.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    @JsonPropertyDescription("The content provider code under which traffic is billed.")
    private Integer cpcode;
    /**
     * The date and time of a download event on a Ghost server.
     * (Required)
     * 
     */
    @JsonProperty("downloadTime")
    @JsonPropertyDescription("The date and time of a download event on a Ghost server.")
    private Date downloadTime;
    /**
     * The unique identifier of a vehicle.
     * (Required)
     * 
     */
    @JsonProperty("uid")
    @JsonPropertyDescription("The unique identifier of a vehicle.")
    private String uid;
    /**
     * The host of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("host")
    @JsonPropertyDescription("The host of a downloaded file.")
    private String host;
    /**
     * The path of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The path of a downloaded file.")
    private String path;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The content provider code under which traffic is billed.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    public Integer getCpcode() {
        return cpcode;
    }

    /**
     * The content provider code under which traffic is billed.
     * (Required)
     * 
     */
    @JsonProperty("cpcode")
    public void setCpcode(Integer cpcode) {
        this.cpcode = cpcode;
    }

    /**
     * The date and time of a download event on a Ghost server.
     * (Required)
     * 
     */
    @JsonProperty("downloadTime")
    public Date getDownloadTime() {
        return downloadTime;
    }

    /**
     * The date and time of a download event on a Ghost server.
     * (Required)
     * 
     */
    @JsonProperty("downloadTime")
    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    /**
     * The unique identifier of a vehicle.
     * (Required)
     * 
     */
    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    /**
     * The unique identifier of a vehicle.
     * (Required)
     * 
     */
    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * The host of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    /**
     * The host of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * The path of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The path of a downloaded file.
     * (Required)
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
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
        return new ToStringBuilder(this).append("cpcode", cpcode).append("downloadTime", downloadTime).append("uid", uid).append("host", host).append("path", path).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uid).append(path).append(cpcode).append(host).append(additionalProperties).append(downloadTime).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Notification) == false) {
            return false;
        }
        Notification rhs = ((Notification) other);
        return new EqualsBuilder().append(uid, rhs.uid).append(path, rhs.path).append(cpcode, rhs.cpcode).append(host, rhs.host).append(additionalProperties, rhs.additionalProperties).append(downloadTime, rhs.downloadTime).isEquals();
    }

}
