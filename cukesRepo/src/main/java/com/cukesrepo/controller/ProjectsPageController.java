package com.cukesrepo.controller;

import com.cukesrepo.repository.ProjectRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectsPageController {


    private final ProjectRepository _projectRepository;

    @Autowired
    public ProjectsPageController(ProjectRepository projectRepository)
    {
        Validate.notNull(projectRepository, "projectRepository cannot be null");

        _projectRepository = projectRepository;

    }

    @RequestMapping(value = "/projects/", method = RequestMethod.GET)
    protected ModelAndView projectsPage
            (

            )
    {

        ModelAndView model = new ModelAndView("ProjectsPage");

        model.addObject("projects", _projectRepository.getProjects());

        return model;
    }

}