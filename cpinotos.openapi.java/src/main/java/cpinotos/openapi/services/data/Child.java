
package cpinotos.openapi.services.data;

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
    "name",
    "uuid",
    "templateUuid",
    "templateLink",
    "behaviors",
    "criteria",
    "criteriaMustSatisfy",
    "criteriaLocked",
    "children",
    "comments"
})
public class Child {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("templateUuid")
    private String templateUuid;
    @JsonProperty("templateLink")
    private String templateLink;
    @JsonProperty("behaviors")
    private List<Object> behaviors = new ArrayList<Object>();
    @JsonProperty("criteria")
    private List<Object> criteria = new ArrayList<Object>();
    @JsonProperty("criteriaMustSatisfy")
    private Child.CriteriaMustSatisfy criteriaMustSatisfy = Child.CriteriaMustSatisfy.fromValue("all");
    @JsonProperty("criteriaLocked")
    private Boolean criteriaLocked;
    @JsonProperty("children")
    private List<Child> children = new ArrayList<Child>();
    @JsonProperty("comments")
    private String comments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("templateUuid")
    public String getTemplateUuid() {
        return templateUuid;
    }

    @JsonProperty("templateUuid")
    public void setTemplateUuid(String templateUuid) {
        this.templateUuid = templateUuid;
    }

    @JsonProperty("templateLink")
    public String getTemplateLink() {
        return templateLink;
    }

    @JsonProperty("templateLink")
    public void setTemplateLink(String templateLink) {
        this.templateLink = templateLink;
    }

    @JsonProperty("behaviors")
    public List<Object> getBehaviors() {
        return behaviors;
    }

    @JsonProperty("behaviors")
    public void setBehaviors(List<Object> behaviors) {
        this.behaviors = behaviors;
    }

    @JsonProperty("criteria")
    public List<Object> getCriteria() {
        return criteria;
    }

    @JsonProperty("criteria")
    public void setCriteria(List<Object> criteria) {
        this.criteria = criteria;
    }

    @JsonProperty("criteriaMustSatisfy")
    public Child.CriteriaMustSatisfy getCriteriaMustSatisfy() {
        return criteriaMustSatisfy;
    }

    @JsonProperty("criteriaMustSatisfy")
    public void setCriteriaMustSatisfy(Child.CriteriaMustSatisfy criteriaMustSatisfy) {
        this.criteriaMustSatisfy = criteriaMustSatisfy;
    }

    @JsonProperty("criteriaLocked")
    public Boolean getCriteriaLocked() {
        return criteriaLocked;
    }

    @JsonProperty("criteriaLocked")
    public void setCriteriaLocked(Boolean criteriaLocked) {
        this.criteriaLocked = criteriaLocked;
    }

    @JsonProperty("children")
    public List<Child> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
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
        return new ToStringBuilder(this).append("name", name).append("uuid", uuid).append("templateUuid", templateUuid).append("templateLink", templateLink).append("behaviors", behaviors).append("criteria", criteria).append("criteriaMustSatisfy", criteriaMustSatisfy).append("criteriaLocked", criteriaLocked).append("children", children).append("comments", comments).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(templateUuid).append(comments).append(templateLink).append(children).append(behaviors).append(criteria).append(name).append(criteriaLocked).append(criteriaMustSatisfy).append(additionalProperties).append(uuid).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Child) == false) {
            return false;
        }
        Child rhs = ((Child) other);
        return new EqualsBuilder().append(templateUuid, rhs.templateUuid).append(comments, rhs.comments).append(templateLink, rhs.templateLink).append(children, rhs.children).append(behaviors, rhs.behaviors).append(criteria, rhs.criteria).append(name, rhs.name).append(criteriaLocked, rhs.criteriaLocked).append(criteriaMustSatisfy, rhs.criteriaMustSatisfy).append(additionalProperties, rhs.additionalProperties).append(uuid, rhs.uuid).isEquals();
    }

    public enum CriteriaMustSatisfy {

        ALL("all"),
        ANY("any");
        private final String value;
        private final static Map<String, Child.CriteriaMustSatisfy> CONSTANTS = new HashMap<String, Child.CriteriaMustSatisfy>();

        static {
            for (Child.CriteriaMustSatisfy c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private CriteriaMustSatisfy(String value) {
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
        public static Child.CriteriaMustSatisfy fromValue(String value) {
            Child.CriteriaMustSatisfy constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
