package com.cukesrepo.controller;


import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.page.FeaturesPage;

import com.cukesrepo.service.email.EmailService;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class FeaturesPageController {

    public FeatureService _featureService;
    public ProjectService _projectService;
    public EmailService _emailService;


    @Autowired

    public FeaturesPageController(FeatureService featureService,ProjectService projectService,EmailService emailService) {

        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectService = projectService;
        _emailService=emailService;

}

    @RequestMapping(value = {"/projects/{projectName}/"})
    @ResponseBody
    public void featurespage(HtmlCanvas html, @PathVariable String projectName) throws IOException, ProjectNotFoundException {

        html.render(new FeaturesPage(_featureService,_projectService,_emailService,_projectService.getProjectByName(projectName)));

   


    }


}