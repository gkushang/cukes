package com.cukesrepo.repository;


import com.cukesrepo.domain.Project;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository
{

    private final MongoTemplate _mongoTemplate;
    private List<Project> _projects = new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(ProjectRepository.class);

    @Autowired
    public ProjectRepository(MongoTemplate mongoTemplate)
    {
        Validate.notNull(mongoTemplate, "mongoTemplate cannot be null");

        _mongoTemplate = mongoTemplate;

    }

    public List<Project> getProjects()
    {
        LOG.info("Querying db to get all the projects");

        _projects = _mongoTemplate.find(new Query(), Project.class);

        return _projects;
    }

    public Optional<Project> getProjectByName(String projectName)
    {
        Query query = new Query(Criteria.where(Project.NAME).is(projectName));

        Project project = _mongoTemplate.findOne(query, Project.class);

        LOG.info("Project '{}' found from db", projectName);

        return Optional.fromNullable(project);
    }

}


