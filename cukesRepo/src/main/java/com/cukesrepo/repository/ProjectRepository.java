package com.cukesrepo.repository;


import com.cukesrepo.domain.CukesProjects;
import com.cukesrepo.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ProjectRepository
{

    private final String _workspace;
    private final String _project1Name;
    private final String _project2Name;
    private CukesProjects _cukesProjects;

    @Autowired
    public ProjectRepository(@Value("${project.workspace}") String workspace, @Value("${project.one}") String project1Name,
                             @Value("${project.two}") String project2Name)
    {
         _cukesProjects = new CukesProjects();
        _workspace = workspace;
        _project1Name =project1Name;
        _project2Name=project2Name;
        _setupDefaultProjects();
    }

    public ArrayList<Project> getProjects()
    {
        return _cukesProjects.getProjects();
    }

    public void setProjects(ArrayList<Project> projects)
    {
        _cukesProjects.setProjects(projects);
    }

    private void _setupDefaultProjects()
    {

        ArrayList<Project> projects = new ArrayList<Project>();
        Project oraTest = new Project();
        oraTest.setName(_project1Name);
        oraTest.setRepositoryPath(_workspace + "/" + _project1Name);

        Project campaignManagerTest = new Project();
        campaignManagerTest.setName(_project2Name);
        campaignManagerTest.setRepositoryPath(_workspace + "/" + _project2Name);

        projects.add(oraTest);
        projects.add(campaignManagerTest);

        setProjects(projects);
    }

    public Project getProjectByName(String projectName)
    {
        for(Project project : getProjects())
        {
            if(project.getName().equalsIgnoreCase(projectName))
                return project;
        }

        return null;
    }
}

