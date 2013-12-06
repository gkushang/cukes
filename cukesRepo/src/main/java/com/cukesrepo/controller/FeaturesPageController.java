package com.cukesrepo.controller;

import com.cukesrepo.service.EmailService;
import com.cukesrepo.service.FeatureService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

@Controller
public class FeaturesPageController {

    private final FeatureService _featureService;
    private final EmailService _emailService;

    @Autowired
    public FeaturesPageController(FeatureService featureService, EmailService emailService)
    {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _emailService = emailService;
    }

    @RequestMapping(value = {"/projects/{project}/"}, method = RequestMethod.GET)
    protected ModelAndView featuresPage
    (
            @PathVariable String project
    ) throws MessagingException {

        ModelAndView model = new ModelAndView("FeaturesPage");

        model.addObject("features", _featureService.fetch(project));
        model.addObject("totalScenarios", _featureService.getTotalNumberOfScenarios());


        return model;
    }

    @RequestMapping(value = {"/service/email"}, method = RequestMethod.GET)
    protected @ResponseBody String sendEmail() throws MessagingException {
        _emailService.send();
        return "email";

    }

}