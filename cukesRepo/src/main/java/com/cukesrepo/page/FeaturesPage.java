package com.cukesrepo.page;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.FeatureStatus;
import com.cukesrepo.domain.Project;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.service.email.EmailService;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.*;

public class FeaturesPage implements Renderable {
    public FeatureService _featureService;
    public ProjectService _projectService;
    public EmailService _emailService;
    private Project _project;

    public FeaturesPage(FeatureService featureService, ProjectService projectService, EmailService emailService, Project project) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectService = projectService;
        _emailService = emailService;
        _project = project;

    }

    private void addScriptsAndStyleSheets(HtmlCanvas html) throws IOException {
        html.head()
                .macros().javascript("/../../resources/jquery-1.10.2.min.js")
                .macros().javascript("/../../resources/email.js")
                .macros().javascript("/../../resources/canvasjs.min.js").macros()
                .javascript("/../../resources/firstword.js").macros()
                .stylesheet("/../../resources/style1.css").macros().stylesheet("/../../resources/sprites.css")._head();
    }

    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        int cumulativeScenarios = 0;
        addScriptsAndStyleSheets(html);
        int alternate = 0;

        html
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
                ._div();

        html.div(id("secondpage"));

        html.h4().span().content(_project.getName())._h4();

        html.table(id("customers"))
                .tr().th().content("Features").th().content("No.of scenarios").th().content("% Approved").th().content("Cuke Sniffer").th().content("Status").th().content("")._tr();
        try {
            for (Feature feature : _featureService.fetchFeatures(_project)) {
                alternate = alternate + 1;
                String featurehidden = "feature" + (feature.getId());
                String emailhidden = "emailbutton" + (feature.getId());
                cumulativeScenarios = cumulativeScenarios + feature.getTotalScenarios();
                html.input(type("hidden").id(featurehidden).value(feature.getId()));
                html.input(type("hidden").id("projectName").value(_project.getName()));
                if (alternate % 2 == 0)
                    html.tr(class_("alt"));
                else
                    html.tr();
                html.td().a(href("")).span().content(feature.getName())._a()._td()
                        .td().span().content(Integer.toString(feature.getTotalScenarios()))._td()

                        .td().span().content(Float.toString(feature.getTotalApprovedScenarios()))._td()
                        .td().span().content("good/bad")._td()
                        .td().span().content(feature.getStatus())._td();

                if (feature.getStatus().equalsIgnoreCase(FeatureStatus.APPROVED.get())) {
                    html.td().input(type("button").id(emailhidden).value("Send for Review").disabled("Approved"))._td();
                }

                if (feature.getStatus().equalsIgnoreCase(FeatureStatus.UNDER_REVIEW.get())) {
                    html.td().input(type("button").id(emailhidden).value("Resend Email for Review"))._td();
                }
                if (feature.getStatus().equalsIgnoreCase(FeatureStatus.NEED_REVIEW.get())) {
                    html.td().input(type("button").id(emailhidden).value("Send Email for Review"))._td();
                }

                html._tr();
            }
        } catch (FeatureNotFoundException e) {
            e.printStackTrace();
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }

        html.tfoot().tr().td().content("Total No of scenarios").td().content(Integer.toString(cumulativeScenarios)).td().content("").td().content("").td().content("").td().content("")._tr()._tfoot();
        html._table();

        html._div();
        html._body();

    }
}
