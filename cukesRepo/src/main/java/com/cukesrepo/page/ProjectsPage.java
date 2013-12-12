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
                .stylesheet("/../../resources/style1.css").macros().stylesheet("/../../resources/sprites.css").macros().stylesheet("/../../resources/main.css")._head();
    }


    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        addScriptsAndStyleSheets(html);
       html
               .body()
                .div(class_("middle-page"))
                .span(class_("colorDarkGreen"))
                .content("Welcome, K.Gajjar")
                .span(class_("floatRight colorDarkGreen"))
                .content("Logout")
                ._div()
                .div(class_("fullWidthWrapper bgColorA"))
                .div(class_("middle-page"))
                .div(class_("pageTitle"))
                .span(class_("title"))
                .content("cukesRepo")
                .a(href("#").class_("myButton"))
                .content("Settings")
                .a(href("#").class_("myButton"))
                .content("Dashboard")
                .a(href("#").class_("myButton"))
                .content("Home")
                ._div()
                ._div()
                ._div()
                .div(class_("fullWidthWrapper"))
                .div(class_("middle-page"))
                .div(id("projectList").class_("twoDivEqualHalf roundBorder"))
                .span(class_("whiteFont"));
        for (int i = 0; i < _projectService.getProjects().size(); i++) {
            html.br();
            html.a(href("")).content(_projectService.getProjects().get(i).getName());
        }
                html._span()._div()
                .div(id("extraList").class_("twoDivEqualHalf roundBorder"))
                .span(class_("whiteFont"))
                .content("Something will appear here")
                ._div()
                ._div()
                ._div()
                .div(class_("fullWidthWrapper bgColorA"))
                .div(class_("middle-page"))
                .span(class_("whiteFont"))
                .content("2013 Cukes Repo")
                ._div()
                ._div()
                ._body()
        ;

//        for (int i = 0; i < _projectService.getProjects().size(); i++) {
//            html.br();
//            html.a(href("")).content(_projectService.getProjects().get(i).getName());
//        }

    }
}
