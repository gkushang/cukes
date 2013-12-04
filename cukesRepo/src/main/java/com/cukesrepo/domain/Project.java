package com.cukesrepo.domain;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
      "features",
      "name",
      "repositorypath"
})

public class Project {

    @JsonProperty("features")
    private ArrayList<Feature> features = new ArrayList<Feature>();

    @JsonProperty("name")
    private String name;


    @JsonProperty("repositorypath")
    private String repositoryPath;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("features")
    public ArrayList<Feature> getScenarios() {
        return features;
    }

    @JsonProperty("features")
    public void setScenarios(ArrayList<Feature> features) {
        this.features = features;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("repositorypath")
    public String getRepositoryPath() {
        return repositoryPath;
    }

    @JsonProperty("repositorypath")
    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
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