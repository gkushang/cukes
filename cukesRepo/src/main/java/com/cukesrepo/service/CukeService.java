package com.cukesrepo.service;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class CukeService
{

    private FeatureService _featureService;

    @Autowired
    public CukeService(FeatureService featureService)
    {
        Validate.notNull(featureService, "featureService cannot be null.");

        _featureService = featureService;
    }

    public TreeSet<FeatureFile> getFeatures()
    {
        return _featureService.getFeatures();
    }

    public TreeSet<FeatureFile> readFeatures()
    {
        return _featureService.readFeatures();
    }

    public FeatureFile getFeatureByEndPoint(String featureEndPoint)
    {
        return _featureService.getFeatureByEndPoint(featureEndPoint);
    }

}
