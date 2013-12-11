package com.cukesrepo.page;

import com.cukesrepo.service.EmailService;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import java.io.IOException;

/**
 * Created by maduraisamy on 12/10/13.
 */
public class EmailPage {

    public FeatureService _featureService;
    public ProjectService _projectService;
    public EmailService _emailService;


    public EmailPage(FeatureService featureService, ProjectService projectService, EmailService emailService) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectService = projectService;
        _emailService = emailService;

    }


    public void renderOn() throws IOException {

    }
}
