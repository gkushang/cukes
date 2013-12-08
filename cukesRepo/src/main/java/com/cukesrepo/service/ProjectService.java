package com.cukesrepo.service;

import com.cukesrepo.domain.Project;
import com.cukesrepo.repository.ProjectRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository _projectRepository;

    @Autowired
    public ProjectService
            (
                    ProjectRepository projectRepository
            ) {

        Validate.notNull(projectRepository, "projectRepository cannot be null");

        _projectRepository = projectRepository;
    }

    public List<Project> getProjects() {

        return _projectRepository.getProjects();
    }

}
