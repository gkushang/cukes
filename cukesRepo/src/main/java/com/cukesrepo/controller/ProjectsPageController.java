package com.cukesrepo.controller;

import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectsPageController {


    private final ProjectService _projectService;

    @Autowired
    public ProjectsPageController
            (
                    ProjectService projectService
            ) {

        Validate.notNull(projectService, "projectService cannot be null");

        _projectService = projectService;

    }

    @RequestMapping(value = "/projects/", method = RequestMethod.GET)
    protected ModelAndView projectsPage
            (

            ) {

        ModelAndView model = new ModelAndView("ProjectsPage");

        model.addObject("projects", _projectService.getProjects());

        return model;
    }

}