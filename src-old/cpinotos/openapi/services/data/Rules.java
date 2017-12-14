
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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "behaviors",
    "children",
    "options",
    "variables",
    "advancedOverride",
    "customOverride",
    "comments"
})
public class Rules {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    @JsonProperty("behaviors")
    private List<Object> behaviors = new ArrayList<Object>();
    @JsonProperty("children")
    private List<Child> children = new ArrayList<Child>();
    @JsonProperty("options")
    private Options options;
    @JsonProperty("variables")
    private List<Variable> variables = new ArrayList<Variable>();
    @JsonProperty("advancedOverride")
    private String advancedOverride;
    @JsonProperty("customOverride")
    private CustomOverride customOverride;
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

    @JsonProperty("behaviors")
    public List<Object> getBehaviors() {
        return behaviors;
    }

    @JsonProperty("behaviors")
    public void setBehaviors(List<Object> behaviors) {
        this.behaviors = behaviors;
    }

    @JsonProperty("children")
    public List<Child> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @JsonProperty("options")
    public Options getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(Options options) {
        this.options = options;
    }

    @JsonProperty("variables")
    public List<Variable> getVariables() {
        return variables;
    }

    @JsonProperty("variables")
    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    @JsonProperty("advancedOverride")
    public String getAdvancedOverride() {
        return advancedOverride;
    }

    @JsonProperty("advancedOverride")
    public void setAdvancedOverride(String advancedOverride) {
        this.advancedOverride = advancedOverride;
    }

    @JsonProperty("customOverride")
    public CustomOverride getCustomOverride() {
        return customOverride;
    }

    @JsonProperty("customOverride")
    public void setCustomOverride(CustomOverride customOverride) {
        this.customOverride = customOverride;
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
        return new ToStringBuilder(this).append("name", name).append("behaviors", behaviors).append("children", children).append("options", options).append("variables", variables).append("advancedOverride", advancedOverride).append("customOverride", customOverride).append("comments", comments).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(variables).append(comments).append(children).append(behaviors).append(name).append(options).append(advancedOverride).append(additionalProperties).append(customOverride).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Rules) == false) {
            return false;
        }
        Rules rhs = ((Rules) other);
        return new EqualsBuilder().append(variables, rhs.variables).append(comments, rhs.comments).append(children, rhs.children).append(behaviors, rhs.behaviors).append(name, rhs.name).append(options, rhs.options).append(advancedOverride, rhs.advancedOverride).append(additionalProperties, rhs.additionalProperties).append(customOverride, rhs.customOverride).isEquals();
    }

}
