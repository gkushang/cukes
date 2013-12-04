package com.cukesrepo.service;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.repository.FeatureRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FeatureService
{
    private FeatureRepository _featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository)
    {
        Validate.notNull(featureRepository, "featureRepository cannot be null.");
        _featureRepository = featureRepository;
    }

    public ArrayList<Feature> fetch(String projectName)
    {
        return _featureRepository.fetch(projectName);
    }

    public ArrayList<Feature> getFeatures()
    {
        return _featureRepository.getFeatures();
    }


}
