package com.cukesrepo.domain;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
      "projects"
})

public class Projects {

    @JsonProperty("projects")
    private ArrayList<Project> projects = new ArrayList<Project>();

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("projects")
    public ArrayList<Project> getProjects() {
        return projects;
    }

    @JsonProperty("projects")
    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}