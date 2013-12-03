package com.cukesrepo.service;

import com.cukesrepo.repository.FeatureRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

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

    public TreeSet<FeatureFile> readFeatures()
    {
        return _featureRepository.readFeatures();
    }

    public TreeSet<FeatureFile> getFeatures()
    {
        return _featureRepository.getFeatures();
    }

    public FeatureFile getFeatureByEndPoint(String featureEndPoint)
    {
        return _featureRepository.getFeatureByEndPoint(featureEndPoint);
    }

}
