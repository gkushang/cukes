package com.cukesrepo.page;


import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.*;

public class ProjectsPage implements Renderable {
    public ProjectService _projectService;

    public ProjectsPage(ProjectService projectService) {

        Validate.notNull(projectService, "projectService cannot be null");

        _projectService = projectService;

    }

    private void addScriptsAndStyleSheets(HtmlCanvas html) throws IOException {
        html.head()
                .title()
                .content("Cukes Repo")
                .macros().javascript("/../../resources/jquery-1.10.2.min.js")
                .macros().javascript("/../../resources/email.js")
                .macros().javascript("/../../resources/canvasjs.min.js").macros()
                .javascript("/../../resources/firstword.js").macros()
                .stylesheet("/../../resources/style1.css").macros().stylesheet("/../../resources/sprites.css")._head();
    }


    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        addScriptsAndStyleSheets(html);
        html.html()
                .body()
                .div(class_("fullWidthWrapper bgColorA"))
                .div(class_("pageTitle"))
                .span(class_("title"))
                .content("CUKESREPO")
                ._div()
                ._div()

                .div(class_("full-length"))
                .ul()
                .li()
                .a(href("#home").class_("full"))
                .content("Home")
                ._li()
                .li()
                .a(href("#news").class_("full"))
                .content("News")
                ._li()
                .li()
                .a(href("#contact").class_("full"))
                .content("Contact")
                ._li()
                .li()
                .a(href("#about").class_("full"))
                .content("About")
                ._li()
                ._ul()
                ._div()

        .h3().span().content("Projects:")._h3();
        for (int i = 0; i < _projectService.getProjects().size(); i++) {
           html.h1().a(href("")).content(_projectService.getProjects().get(i).getName())._h1();
        }

               html ._body()
                .html() ;



    }
}
