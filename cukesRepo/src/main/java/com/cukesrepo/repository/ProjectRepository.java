package com.cukesrepo.repository;


import com.cukesrepo.domain.CukesProjects;
import com.cukesrepo.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ProjectRepository
{

    private CukesProjects _cukesProjects;

    public ProjectRepository()
    {
         _cukesProjects = new CukesProjects();
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
        oraTest.setName("oraTest");
        oraTest.setRepositoryPath("/Users/kugajjar/Documents/workspace/oraTest");

        Project campaignManagerTest = new Project();
        campaignManagerTest.setName("campaignManagerTest");
        campaignManagerTest.setRepositoryPath("/Users/kugajjar/Documents/workspace/campaignManagerTest");

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

