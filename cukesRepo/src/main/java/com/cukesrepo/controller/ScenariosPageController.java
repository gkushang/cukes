package com.cukesrepo.controller;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ScenarioService;
import com.google.common.base.Optional;
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

        Optional<Feature> feature = _featureService.getFeatureById(projectName, featureId);

        ModelAndView model = new ModelAndView("ScenarioPage");

        if (feature.isPresent()) {
            model.addObject("feature", feature.get());
            model.addObject("scenarios", _scenarioService.getScenariosByFeature(feature.get()));

        } else {
            //add error scenarios here if feature not found
        }

        return model;
    }

}