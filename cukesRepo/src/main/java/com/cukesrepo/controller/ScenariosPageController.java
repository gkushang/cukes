package com.cukesrepo.controller;

import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.exceptions.ScenariosNotFoundException;
import com.cukesrepo.page.ProjectsPage;
import com.cukesrepo.page.ScenariosPage;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import com.cukesrepo.service.scenario.ScenarioService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ScenariosPageController {


    private final ScenarioService _scenarioService;
    private final FeatureService _featureService;
    private final ProjectService _projectService;

    @Autowired
    public ScenariosPageController
            (
                    ProjectService projectService,
                    FeatureService featureService,
                    ScenarioService scenarioService
            ) {

        Validate.notNull(projectService, "projectService cannot be null");
        Validate.notNull(featureService, "featureService cannot be null");
        Validate.notNull(scenarioService, "scenarioService cannot be null");

        _projectService = projectService;
        _featureService = featureService;
        _scenarioService = scenarioService;
    }

    @RequestMapping(value = "projects/{projectName}/{featureId}/")
    @ResponseBody
    public void scenariosPage(HtmlCanvas html, @PathVariable String projectName, @PathVariable String featureId) throws IOException, ProjectNotFoundException {
//        html.render(new ScenariosPage(_projectService,_featureService,_scenarioService,_projectService.getProjectByName(projectName)));
        html.render
                (
                    new ScenariosPage
                        (
                            _projectService,
                            _featureService,
                                _scenarioService,
                            projectName,
                            featureId
                        )
                );


    }

//    @RequestMapping(value = "projects/{projectName}/{featureId}/{scenarioNumber}/approved", method = RequestMethod.GET)
//    protected ModelAndView approveScenario
//            (
//                    @PathVariable String projectName,
//                    @PathVariable String featureId,
//                    @PathVariable String scenarioNumber
//            ) {
//
//        //TODO - temporary code for controller. this will be replaced by actual htmls
//
//        try {
//
//            _scenarioService.approveScenario(projectName, featureId, scenarioNumber);
//
//        } catch (ScenariosNotFoundException e) {
//
//        }
//
//        return scenariosPage(projectName, featureId);
//
//    }
//
//    @RequestMapping(value = "projects/{projectName}/{featureId}/{scenarioNumber}/comments", method = RequestMethod.POST)
//    protected ModelAndView commentPage
//            (
//                    @PathVariable String projectName,
//                    @PathVariable String featureId,
//                    @PathVariable String scenarioNumber,
//                    HttpServletRequest request
//            ) {
//
//
//        //TODO - temporary code for controller. this will be replaced by actual htmls
//
//        System.out.print("\nNumber = " + scenarioNumber + "\n");
//        try {
//            _scenarioService.addComment(projectName, featureId, scenarioNumber, request.getParameter("text" + scenarioNumber));
//
//        } catch (ScenariosNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return scenariosPage(projectName, featureId);
//
//    }
//

}