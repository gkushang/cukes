package com.cukesrepo.controller;


import com.cukesrepo.service.email.EmailService;
import com.cukesrepo.service.feature.FeatureService;
import com.cukesrepo.service.project.ProjectService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class EmailController {
    public FeatureService _featureService;
    public ProjectService _projectService;
    public EmailService _emailService;


    @Autowired
    public EmailController(FeatureService featureService, ProjectService projectService, EmailService emailService) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectService = projectService;
        _emailService = emailService;

    }

    @RequestMapping(value = {"/test/email/{feature}/{project}"})
    @ResponseBody
    public void emailPage(@PathVariable String feature,@PathVariable String project) throws IOException {


        String toEmailAddress = "mahesh.karthikd@gmail.com";
        String emailSubject="Review request for "+feature+" feature in "+project;
        String emailBody = "Hi the link is http://localhost:8800/projects/"+project+"/"+feature+"/";
        System.out.print("I am here"+feature);
        String email = _emailService.send(toEmailAddress, emailSubject, emailBody);


    }

}
