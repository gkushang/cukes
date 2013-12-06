package com.cukesrepo.repository;


import com.cukesrepo.component.GitComponent;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GitRepository
{

    private final ProjectRepository _projectRepository;
    private final GitComponent _gitComponent;

    private static final Logger LOG = LoggerFactory.getLogger(GitRepository.class);

    @Autowired
    public GitRepository
            (
                    ProjectRepository projectRepository,
                    GitComponent gitComponent
            )
    {
        Validate.notNull(projectRepository, "projectRepository cannot be null");
        Validate.notNull(gitComponent, "gitComponent cannot be null");


        _projectRepository = projectRepository;
        _gitComponent = gitComponent;

    }

    public List<Feature> fetchFeatures(String projectName)
    {
        Optional<Project> project = _projectRepository.getProjectByName(projectName);

        LOG.info("Fetching features from Git/Local repository for the project '{}'", projectName);

        if(!project.isPresent()) throw new RuntimeException("Project '"+ projectName +"'is not present");

        return _gitComponent.fetch(project.get());
    }
}
