package com.cukesrepo.controller;


import com.cukesrepo.domain.Project;
import com.cukesrepo.page.FeaturesPage;
import com.cukesrepo.page.ProjectsPage;
import com.cukesrepo.service.EmailService;

import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ProjectNotFoundException;

import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.for_;

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
    public void featurespage(HtmlCanvas html, @PathVariable String projectName) throws IOException {
        html.render(new FeaturesPage(_featureService,_projectService,_emailService,projectName));

   


    }


}