package com.cukesrepo.page;

import com.cukesrepo.domain.*;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import com.cukesrepo.service.scenario.ScenarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.w3c.tidy.Report;

import java.io.*;
import java.util.List;

import static org.rendersnake.HtmlAttributesFactory.*;

/**
 * Created by maduraisamy on 12/12/13.
 */
public class ScenariosPage implements Renderable {

    private final String _projectName;
    private final String _featureId;
    public FeatureService _featureService;
    public ProjectService _projectService;
    public ScenarioService _scenarioService;


    public ScenariosPage(ProjectService projectService, FeatureService featureService, ScenarioService scenarioService, String projectName, String featureId) {

        Validate.notNull(projectService, "projectService cannot be null");

        _projectService = projectService;
        _featureService = featureService;
        _scenarioService = scenarioService;
        _projectName = projectName;
        _featureId = featureId;


    }

    private void addScriptsAndStyleSheets(HtmlCanvas html) throws IOException {
        html.head()
                .title()
                .content("Cukes Repo")
                .macros().javascript("/../../resources/jquery-1.10.2.min.js")
                .macros().javascript("/../../resources/email.js")
                .macros().javascript("/../../resources/canvasjs.min.js").macros()
                .javascript("/../../resources/firstword.js").macros()
                .javascript("/../../resources/scrollTo.js").macros()
                .stylesheet("/../../resources/style1.css").macros().stylesheet("/../../resources/sprites.css")._head();
    }

    private HtmlCanvas html;
    private StringBuilder json;

    private StringBuilder parseAFeatureFile()
            throws IOException,
            RuntimeException, FeatureNotFoundException {


      //  String code=_featureService.getFeatureName("oraTest","error-page").toString();
        //System.out.println(code);
        //FileUtils.writeStringToFile(new File("src/main/resources/output/gherkin.feature"), code);
        String gherkin = FixJava.readReader(new InputStreamReader(
                new FileInputStream("src/main/resources/output/gherkin.feature"), "UTF-8"));
        json = new StringBuilder();
        JSONFormatter formatter = new JSONFormatter(json);
       Parser parser = new Parser(formatter);

        parser.parse(gherkin, "src/main/resources/output/gherkin.feature", 0);
        formatter.done();
        formatter.close();
        System.out.println(json.toString()); // Gherkin source as JSON
        return json;
    }

    private List<Feature> features;

    private void addLeftNavigationPane(HtmlCanvas html) throws Throwable {
        html.div(class_("full-height"));
        html.ul();
        for (Feature feature : features) {
            System.out.println(feature.getName());

            html.li().a(href("").class_("full-h")).span().content(feature.getName())._a()._li();
            html.br();
        }
        html._ul();
        html._div();
//		html.hr(class_("vertical"));
    }

    private void addScenarioDiv(int i,HtmlCanvas html) throws Throwable {
        html.div(id("scenario" + (i + 1))); //need to close
    }

    private void addParentChildTag(List<Tag> tags, String className,HtmlCanvas html)
            throws Throwable {
        String parent_child_tag = "";
        for (Tag tag : tags) {
            parent_child_tag = parent_child_tag + tag.getName() + " ";
        }
        html.span(class_("ptag")).content(parent_child_tag);
    }

    private void addSteps(List<Step> steps,HtmlCanvas html) throws Throwable {
        for (Step step : steps) {
            String complete_step = step.getKeyword() + step.getName();

            complete_step = complete_step.replaceAll("\u003c", "&lt");
            complete_step = complete_step.replaceAll("\u003e", "&gt");
            html.span(class_("step")).content(complete_step);
            List<Row> rows = step.getRows();
            if (rows.size() != 0) {
                html.table(class_("datatable"));
                html.tbody();
                for (int j = 0; j < rows.size(); j++) {
                    html.tr();
                    Row row = rows.get(j);
                    List<String> cells = row.getCells();
                    for (String cell : cells)
                        html.td().content(cell);
                    html._tr();
                }//row
                html._tbody();
                html._table();
            }
        }
    }

    private void addTable(Example example,HtmlCanvas html) throws Throwable {
        html.span(class_("example")).content(
                "Scenarios: " + example.getDescription());
        html.table(class_("example_data"));
        List<Row> rows = example.getRows();
        for (int j = 0; j < rows.size(); j++) {
            if (j == 0)
                html.thead();
            if (j == 1)
                html.tbody();
            html.tr();
            Row row = rows.get(j);
            List<String> cells = row.getCells();
            for (String cell : cells) {
                if (j == 0)
                    html.th(scope("col")).content(cell);
                else
                    html.td().content(cell);
            }//cell
            html._tr();
            if (j == 0)
                html._thead();
        }//row

        html._tbody();
        html._table();
    }

    private void addLinks(HtmlCanvas html) throws Throwable {
        html.div(class_("scenario_links")).content("Scenarios");
        int i = 1;
        Feature feature = features.get(0);
        List<Scenario> scenarios = feature.getScenarios();
        for (Scenario scenario : scenarios) {
            html.div();
            html.a(class_("scenario" + i).href("")).content(scenario.getName());
            html._div();
            i++;
        }
    }


    public void test(HtmlCanvas html) throws Throwable {


//        StringBuilder json = parseAFeatureFile();
//System.out.println("JSON"+json);
//        ObjectMapper mapper = new ObjectMapper();
//        features = mapper.readValue(json.toString(), List.class);
//        String uri = features.get(0).getUri();
//        String fileName = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        html.table().tr().td();
        addLeftNavigationPane(html);
        html._td();
        html.td();
        html.div(id("main-low"));
        addLinks(html);
//        for (Feature feature : features) {
//            List<Tag> tags = feature.getTags();
        Feature feature = _featureService.getFeatureName(_projectName, _featureId).get();

        List<Tag> tags = feature.getTags();

            addParentChildTag(tags, "ptag", html); 
            html.span(class_("feature")).content(feature.getName());
            List<Scenario> scenarios = _scenarioService.fetchScenarios(_projectService.getProjectByName(_projectName), _featureId);

            System.out.println("++++++++++++++++++" + scenarios.size());
            System.out.println(features.size());
            for (int i = 0; i < scenarios.size(); i++) {
                Scenario scenario = scenarios.get(i);
                addScenarioDiv(i,html);
                List<Tag> child_tags = scenario.getTags();
                addParentChildTag(child_tags, "ctag",html);
                html.span(class_("keyword"))
                        .content(scenario.getKeyword() + ":");
                html.span(class_("scenario_description")).content(
                        scenario.getName());
                html.a(class_("top").href("")).content("Top");
                List<Step> steps = scenario.getSteps();
                addSteps(steps,html);
                List<Example> examples = scenario.getExamples();
                if (examples != null) {
                    for (Example example : examples) {
                        addTable(example,html);
                    }//example
                }//scenario
                html._div();
            }


html._div()._td()._tr()._table();

        html._body();


    }
    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        try {
            features= _featureService.fetchFeatures(_projectService.getProjectByName(_projectName));
        } catch (FeatureNotFoundException e) {
            e.printStackTrace();
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }

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
                ._div();

        try {
            test(html);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }



    }
}