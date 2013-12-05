package com.cukesrepo.service;


import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Scenario;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService
{
    private final FeatureService _featureService;

    @Autowired
    public ScenarioService(FeatureService featureService)
    {
        Validate.notNull(featureService, "featureService cannot be null.");

        _featureService = featureService;
    }

    public List<Scenario> getScenario(Feature feature)
    {
        return feature.getScenarios();
    }

}
