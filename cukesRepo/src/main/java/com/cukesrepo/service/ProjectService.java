package com.cukesrepo.service;

import com.cukesrepo.domain.Project;
import com.cukesrepo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectService
{
    private final ProjectRepository _projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository)
    {
        _projectRepository = projectRepository;
    }

    public ArrayList<Project> getProjects()
    {
        return _projectRepository.getProjects();
    }

    public Project getProjectByName(String projectName)
    {
        return _projectRepository.getProjectByName(projectName).get();
    }
}
