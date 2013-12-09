package com.cukesrepo.page;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.service.EmailService;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.springframework.stereotype.Controller;


import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.for_;
import static org.rendersnake.HtmlAttributesFactory.type;

public class FeaturesPage implements Renderable {
    public FeatureService _featureService;
    public EmailService _emailService=new EmailService();
   private String _projectName;

    public FeaturesPage(FeatureService featureService, String projectName) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectName = projectName;
    }

    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        html.h4().content("FEATURE PAGE MAN");
        for(Feature feature:_featureService.fetch(_projectName))
        {
            html.br();
            html .label().content(feature.getName());

            html.label().content(Integer.toString(feature.getTotalScenarios()));
            html.input(type("button").name("test").onClick(_emailService.send()));

        }


    }
}
