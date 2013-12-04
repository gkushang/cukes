package com.cukesrepo.controller;

import com.cukesrepo.service.CukeService;
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


    private final CukeService _cukeService;
    private final FeatureService _featureService;

    @Autowired
    public FeaturesPageController(CukeService cukeService, FeatureService featureService)
    {
        Validate.notNull(cukeService, "cukeService cannot be null");
        Validate.notNull(featureService, "featureService cannot be null");

        _cukeService = cukeService;
        _featureService = featureService;
    }

    @RequestMapping(value = {"/projects/{project}","/projects/{project}/"}, method = RequestMethod.GET)
    protected ModelAndView homePage
    (
            @PathVariable String project
    )
    {

        ModelAndView model = new ModelAndView("FeaturesPage");

        model.addObject("features", _featureService.fetch(project));

        return model;
    }

}