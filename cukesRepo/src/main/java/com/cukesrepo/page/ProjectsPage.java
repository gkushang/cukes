package com.cukesrepo.page;

import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.springframework.stereotype.Controller;


import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.for_;

public class ProjectsPage implements Renderable {
    public  ProjectService _projectService;

    public ProjectsPage(ProjectService projectService) {
        Validate.notNull(projectService, "projectService cannot be null");

        _projectService = projectService;

    }

    @Override
    public void renderOn(HtmlCanvas htmlCanvas) throws IOException {



        htmlCanvas.h4().content("WASSUP MAN");

       for(int i=0;i<_projectService.getProjects().size();i++)
       {
           htmlCanvas.br();
           htmlCanvas .label(for_("name")).content(_projectService.getProjects().get(i).getName());
       }

    }
}
