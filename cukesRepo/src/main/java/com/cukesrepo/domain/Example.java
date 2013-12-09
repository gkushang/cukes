package com.cukesrepo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "description",
        "name",
        "keyword",
        "line",
        "rows"
})
public class Example {

    @JsonProperty("id")
    private String id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("line")
    private Integer line;
    @JsonProperty("rows")
    private List<Row> rows = new ArrayList<Row>();
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

    @JsonProperty("rows")
    public List<Row> getRows() {
        return rows;
    }

    @JsonProperty("rows")
    public void setRows(List<Row> rows) {
        this.rows = rows;
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