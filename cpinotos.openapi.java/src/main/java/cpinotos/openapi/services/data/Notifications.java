
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
 * Encapsulates a collection of download notifications, with additional pagination context.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nextPageStart",
    "notifications"
})
public class Notifications {

    /**
     * The timestamp in milliseconds that indicates the `start` of the next page of notifications.
     * 
     */
    @JsonProperty("nextPageStart")
    @JsonPropertyDescription("The timestamp in milliseconds that indicates the `start` of the next page of notifications.")
    private Object nextPageStart;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifications")
    private List<Notification> notifications = new ArrayList<Notification>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The timestamp in milliseconds that indicates the `start` of the next page of notifications.
     * 
     */
    @JsonProperty("nextPageStart")
    public Object getNextPageStart() {
        return nextPageStart;
    }

    /**
     * The timestamp in milliseconds that indicates the `start` of the next page of notifications.
     * 
     */
    @JsonProperty("nextPageStart")
    public void setNextPageStart(Object nextPageStart) {
        this.nextPageStart = nextPageStart;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifications")
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("notifications")
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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
        return new ToStringBuilder(this).append("nextPageStart", nextPageStart).append("notifications", notifications).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(nextPageStart).append(notifications).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Notifications) == false) {
            return false;
        }
        Notifications rhs = ((Notifications) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(nextPageStart, rhs.nextPageStart).append(notifications, rhs.notifications).isEquals();
    }

}
