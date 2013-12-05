package com.cukesrepo.service;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.repository.FeatureRepository;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FeatureService
{
    private FeatureRepository _featureRepository;
    private int _totalNumberOfScenarios;

    @Autowired
    public FeatureService(FeatureRepository featureRepository)
    {
        Validate.notNull(featureRepository, "featureRepository cannot be null");

        _featureRepository = featureRepository;
    }

    public ArrayList<Feature> fetch(String projectName)
    {
        Validate.notEmpty(projectName, "projectName cannot be null");

        return _featureRepository.fetch(projectName);
    }

    public Optional<Feature> getFeatureById(String featureId)
    {

        Validate.notEmpty(featureId, "featureId cannot be empty");

        for(Feature feature : _featureRepository.getFeatures())
        {
            if(feature.getId().equalsIgnoreCase(featureId))
                return Optional.of(feature);
        }

        return Optional.absent();
    }

    public int getTotalNumberOfScenarios()
    {
        _totalNumberOfScenarios = 0;

        for(Feature feature : _featureRepository.getFeatures())
            _totalNumberOfScenarios += feature.getNumberOfScenarios();

        return _totalNumberOfScenarios;
    }

}
