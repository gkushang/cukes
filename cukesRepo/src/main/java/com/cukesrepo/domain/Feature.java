package com.cukesrepo.domain;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "description",
        "name",
        "keyword",
        "line",
        "elements",
        "uri"
})

@Document(collection = "feature")
public class Feature {

    public static final String PROJECTNAME = "projectname";
    public static final String ID = "_id";

    @Field(PROJECTNAME)
    private String projectName;

    @Field("totalscenarios")
    private int totalScenarios = 0;

    @Field("totalapprovedscenarios")
    private int totalApprovedScenarios = 0;

    @JsonProperty("id")
    @Field(ID)
    private String id;

    @JsonProperty("description")
    @Field("description")
    private String description;

    @Field("name")
    @JsonProperty("name")
    private String name;

    @JsonProperty("keyword")
    @Field("keyword")
    private String keyword;

    @JsonProperty("line")
    private Integer line;

    @Field("scenarios")
    @JsonProperty("elements")
    private List<Scenario> scenarios = new ArrayList<Scenario>();

    @JsonProperty("uri")
    private String uri;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("keyword")
    public String getKeyword() {
        return keyword;
    }

    @JsonProperty("keyword")
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @JsonProperty("line")
    public Integer getLine() {
        return line;
    }

    @JsonProperty("line")
    public void setLine(Integer line) {
        this.line = line;
    }

    @JsonProperty("elements")
    public List<Scenario> getScenarios() {
        return scenarios;
    }

    @JsonProperty("elements")
    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void setTotalScenarios(int totalScenarios)
    {
        this.totalScenarios = totalScenarios;
    }

    public int getTotalScenarios()
    {
        return this.totalScenarios;
    }

    public void setTotalApprovedScenarios(int totalApprovedScenarios)
    {
        this.totalApprovedScenarios = totalApprovedScenarios;
    }

    public int getTotalApprovedScenarios()
    {
        return totalApprovedScenarios;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}