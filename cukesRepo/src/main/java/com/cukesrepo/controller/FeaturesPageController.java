package com.cukesrepo.controller;

import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeaturesPageController {

    private final ProjectService _projectService;
    private final FeatureService _featureService;

    @Autowired
    public FeaturesPageController
            (
                    ProjectService projectService,
                    FeatureService featureService
            ) {

        Validate.notNull(projectService, "projectService cannot be null");
        Validate.notNull(featureService, "featureService cannot be null");

        _projectService = projectService;
        _featureService = featureService;
    }

    @RequestMapping(value = {"/projects/{projectName}/"}, method = RequestMethod.GET)
    protected ModelAndView featuresPage
            (
                    @PathVariable String projectName

            ) throws FeatureNotFoundException, ProjectNotFoundException {

        ModelAndView model = new ModelAndView("FeaturesPage");

        try {
            model.addObject("features", _featureService.fetchFeatures(_projectService.getProjectByName(projectName)));

        } catch (FeatureNotFoundException fe) {

        } catch (ProjectNotFoundException pe) {

        }

        return model;
    }


}