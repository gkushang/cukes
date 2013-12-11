package com.cukesrepo.service.project;

import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.domain.Project;
import com.cukesrepo.repository.project.ProjectRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository _projectRepository;

    @Autowired
    public ProjectServiceImpl
            (
                    ProjectRepository projectRepository
            ) {

        Validate.notNull(projectRepository, "projectRepository cannot be null");

        _projectRepository = projectRepository;
    }

    @Override
    public List<Project> getProjects() {

        return _projectRepository.getProjects();
    }

    @Override
    public Project getProjectByName(String projectName) throws ProjectNotFoundException {

        return _projectRepository.getProjectByName(projectName).get();
    }
}
