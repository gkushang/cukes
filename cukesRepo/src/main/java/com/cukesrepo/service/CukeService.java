package com.cukesrepo.service;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
