package com.cukesrepo.repository.project;


import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.domain.Project;
import com.google.common.base.Optional;

import java.util.List;

public interface ProjectRepository {

    public List<Project> getProjects();

    public Optional<Project> getProjectByName(String projectName) throws ProjectNotFoundException;
}


