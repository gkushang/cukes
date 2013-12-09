package com.cukesrepo.page;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.service.FeatureService;
import com.cukesrepo.service.ProjectService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.springframework.stereotype.Controller;


import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.for_;

public class FeaturesPage implements Renderable {
    public FeatureService _featureService;
   private String _projectName;

    public FeaturesPage(FeatureService featureService, String projectName) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;
        _projectName = projectName;
    }

    @Override
    public void renderOn(HtmlCanvas htmlCanvas) throws IOException {
        htmlCanvas.h4().content("FEATURE PAGE MAN");
        for(Feature feature:_featureService.fetch(_projectName))
        {
            htmlCanvas.br();
            htmlCanvas .label().content(feature.getName());

            htmlCanvas.label().content(Integer.toString(feature.getTotalScenarios()));
        }


    }
}
