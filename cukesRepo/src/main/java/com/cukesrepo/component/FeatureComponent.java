package com.cukesrepo.component;


import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeatureComponent
{
    private final ScenarioComponent _scenarioComponent;

    @Autowired
    public FeatureComponent(ScenarioComponent scenarioComponent)
    {
        Validate.notNull(scenarioComponent, "scenarioComponent cannot be null");

        _scenarioComponent = scenarioComponent;
    }


    public Feature processFeature(Project project, Feature feature)
    {
        Validate.notNull(feature, "feature cannot be null");
        Validate.notNull(project, "project cannot be null");

        _scenarioComponent.processScenarios(feature.getScenarios());

        feature.setTotalScenarios(_scenarioComponent.getNumberOfScenarios());
        feature.setTotalApprovedScenarios(_scenarioComponent.getNumberOfApprovedScenarios());
        feature.setProjectName(project.getName());

        return  feature;
    }

}
