package com.cukesrepo.page;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Row;
import com.cukesrepo.domain.Scenario;
import com.cukesrepo.service.EmailService;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.springframework.stereotype.Controller;
import org.w3c.tidy.Report;


import java.io.IOException;
import java.util.List;

import static org.rendersnake.HtmlAttributesFactory.*;

public class FeaturesPage implements Renderable {
    public FeatureService _featureService;
    public ProjectService _projectService;
    public EmailService _emailService;
    private String _projectName;

    public FeaturesPage(FeatureService featureService, ProjectService projectService, EmailService emailService, String projectName) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectService = projectService;
        _emailService = emailService;
        _projectName = projectName;

    }


    private void addScriptsAndStyleSheets(HtmlCanvas html) throws IOException {
        html.head()
                .macros().javascript("/../../resources/canvasjs.min.js").macros()
                .javascript("/../../resources/firstword.js").macros()
                .stylesheet("/../../resources/style1.css").macros().stylesheet("/../../resources/sprites.css");
    }

    private void addLeftNavigationPane(HtmlCanvas html) throws Throwable {
        html.div(id("cssmenu"));

        html.li().a(href("")).span().content("test")._a()._li();
        html.li().a(href("")).span().content("test1")._a()._li();
        html._div();
        html.hr(class_("vertical"));
        html.div(id("cssmenu"));


    }

    @Override
    public void renderOn(HtmlCanvas html) throws IOException {

        String toEmailAddress = _projectService.getProject(_projectName).toString();
        String emailSubject = "";
        String emailBody = "";
        html
                .html();
        addScriptsAndStyleSheets(html);
        html.macros().javascript("/../../resources/jquery-1.10.2.min.js");
        html.macros().javascript("/../../resources/email.js")
                ._head()
                .body();




        html.table(id("gradient-style"));
        html.tr();
        html.td();
        html.div(id("cssmenu"));
        html.table();
        for (Feature feature : _featureService.fetch(_projectName)) {
            html.tr();
            html.td();
            System.out.println(feature.getName());
            html.a(href("")).span().content(feature.getName())._a();
            html.label().content(Integer.toString(feature.getTotalScenarios()));
            html.input(type("hidden").id("feature").value(feature.getId()));
            html.input(type("hidden").id("projectName").value(_projectName));
           // html.input(type("button").id("emailbutton").value("Button"));
            html._td();
            html._tr();
        }
        html._table();
        html.hr(class_("vertical"));
        html._div();
        html._td();

        html.td();

        html.table();
        List<Feature> features = _featureService.fetch(_projectName);
        for (int j = 0; j < features.size(); j++) {
            if (j == 0)
                html.thead();
            if (j == 1)
                html.tbody();
            html.tr();
            Feature feature = features.get(j);
            List<Scenario> cells = feature.getScenarios();

            for (Scenario cell : cells) {

                    html.td().content(cell.getName());
            }//cell
            html._tr();
            if (j == 0)
                html._thead();
        }
        html._tbody();
        html._table();

        html._td();
        html._tr();

        html._table();




        html._body()
                ._html();
    }
}
