package com.cukesrepo.service.project;

import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.domain.Project;

import java.util.List;


public interface ProjectService {

    public List<Project> getProjects();

    public Project getProjectByName(String projectName) throws ProjectNotFoundException;
}
