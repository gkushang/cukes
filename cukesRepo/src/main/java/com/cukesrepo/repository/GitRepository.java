package com.cukesrepo.repository;


import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ProjectNotFoundException;
import com.cukesrepo.Exceptions.ScenariosNotFoundException;
import com.cukesrepo.component.GitComponent;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Scenario;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GitRepository {

    private final ProjectRepository _projectRepository;
    private final GitComponent _gitComponent;

    private static final Logger LOG = LoggerFactory.getLogger(GitRepository.class);

    @Autowired
    public GitRepository
            (
                    ProjectRepository projectRepository,
                    GitComponent gitComponent
            ) {

        Validate.notNull(projectRepository, "projectRepository cannot be null");
        Validate.notNull(gitComponent, "gitComponent cannot be null");

        _projectRepository = projectRepository;
        _gitComponent = gitComponent;
    }

    public List<Feature> fetchFeatures(String projectName) throws ProjectNotFoundException, FeatureNotFoundException {

        LOG.info("Fetching features from Git/Local repository for the project '{}'", projectName);

        return _gitComponent.fetchFeatures(_getProjectByName(projectName).get());
    }

    public List<Scenario> fetchScenarios(String projectName, String featureId) throws ProjectNotFoundException, ScenariosNotFoundException {

        LOG.info("Fetching scenarios from Git for the project '{}' and Feature '{}'", projectName, featureId);

        return _gitComponent.fetchScenarios(_getProjectByName(projectName).get(), featureId);
    }

    private Optional<Project> _getProjectByName(String projectName) throws ProjectNotFoundException {

        Optional<Project> project = _projectRepository.getProjectByName(projectName);

        LOG.info("Fetch Project '{}' from Git", projectName);

        if (!project.isPresent()) throw new ProjectNotFoundException("Project '" + projectName + "'is not present");

        return project;
    }
}