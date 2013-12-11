package com.cukesrepo.controller;

import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ProjectNotFoundException;
import com.cukesrepo.Exceptions.ScenariosNotFoundException;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ScenarioService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScenariosPageController {


    private final ScenarioService _scenarioService;
    private final FeatureService _featureService;

    @Autowired
    public ScenariosPageController(FeatureService featureService, ScenarioService scenarioService) {
        Validate.notNull(featureService, "featureService cannot be null");
        Validate.notNull(scenarioService, "scenarioService cannot be null");

        _featureService = featureService;
        _scenarioService = scenarioService;
    }

    @RequestMapping(value = "projects/{projectName}/{featureId}/", method = RequestMethod.GET)
    protected ModelAndView scenariosPage
            (
                    @PathVariable String projectName,
                    @PathVariable String featureId
            ) {

        Validate.notNull(projectName, "projectName cannot be null");
        Validate.notNull(featureId, "featureId cannot be null");

        ModelAndView model = new ModelAndView("ScenarioPage");

        try {

            model.addObject("feature.name", _featureService.getFeatureName(projectName, featureId).get());
            model.addObject("scenarios", _scenarioService.fetchScenarios(projectName, featureId));

        } catch (FeatureNotFoundException fe) {

        } catch (ProjectNotFoundException pe) {

        } catch (ScenariosNotFoundException se) {

        }

        return model;
    }

    @RequestMapping(value = "projects/{projectName}/{featureId}/{scenarioNumber}/approved", method = RequestMethod.GET)
    protected
    ModelAndView approveScenario
            (
                    @PathVariable String projectName,
                    @PathVariable String featureId,
                    @PathVariable String scenarioNumber
            ) {

        //TODO - temporary code for controller. this will be replaced by actual htmls

        try {

            _scenarioService.approveScenario(projectName, featureId, scenarioNumber);

        } catch (ScenariosNotFoundException e) {

        }

        return  scenariosPage(projectName, featureId);

    }

    @RequestMapping(value = "projects/{projectName}/{featureId}/{scenarioNumber}/comments/{comment}", method = RequestMethod.GET)
    protected
    ModelAndView commentPage
            (
                    @PathVariable String projectName,
                    @PathVariable String featureId,
                    @PathVariable String scenarioNumber,
                    @PathVariable String comment
            ) {

        //TODO - temporary code for controller. this will be replaced by actual htmls


            try {
                _scenarioService.addComment(projectName, featureId, scenarioNumber, comment);

            } catch (ScenariosNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        return  scenariosPage(projectName, featureId);

    }


}