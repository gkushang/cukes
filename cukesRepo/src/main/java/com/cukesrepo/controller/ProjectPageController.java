package com.cukesrepo.controller;

import com.cukesrepo.page.ProjectsPage;
import com.cukesrepo.service.ProjectService;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


/**
 * Created by maduraisamy on 12/7/13.
 */

@Controller
public class ProjectPageController {
    private final ProjectService _projectService;

    @Autowired
    public ProjectPageController(ProjectService _projectService)
    {
        this._projectService=_projectService;
    }

    @RequestMapping(value = {"/projects/"})
    @ResponseBody
    public void projectspage(HtmlCanvas html) throws IOException {
        html.render(new ProjectsPage(_projectService));


    }
}

