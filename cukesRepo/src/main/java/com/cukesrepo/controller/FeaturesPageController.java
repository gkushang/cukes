package com.cukesrepo.controller;

import com.cukesrepo.service.FeatureService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeaturesPageController {

    private final FeatureService _featureService;

    @Autowired
    public FeaturesPageController(FeatureService featureService)
    {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
    }

    @RequestMapping(value = {"/projects/{project}/"}, method = RequestMethod.GET)
    protected ModelAndView featuresPage
    (
            @PathVariable String project
    )
    {

        ModelAndView model = new ModelAndView("FeaturesPage");

        model.addObject("features", _featureService.fetch(project));
        model.addObject("totalScenarios", _featureService.getTotalNumberOfScenarios());

        return model;
    }

}