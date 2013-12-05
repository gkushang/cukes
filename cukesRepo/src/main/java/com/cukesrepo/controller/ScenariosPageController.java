package com.cukesrepo.controller;

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
    public ScenariosPageController(FeatureService featureService, ScenarioService scenarioService)
    {
        Validate.notNull(featureService, "featureService cannot be null");
        Validate.notNull(scenarioService, "scenarioService cannot be null");

        _featureService = featureService;
        _scenarioService = scenarioService;
    }

    @RequestMapping(value = "projects/{project}/{featureId}/", method = RequestMethod.GET)
	protected ModelAndView scenariosPage
    (
            @PathVariable String featureId
    )
    {

        Validate.notNull(featureId, "featureId cannot be null");

		ModelAndView model = new ModelAndView("ScenarioPage");
        model.addObject("feature", _featureService.getFeatureById(featureId));
        model.addObject("scenarios", _scenarioService.getScenario(_featureService.getFeatureById(featureId)));

		return model;
	}

}