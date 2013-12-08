package com.cukesrepo.service;

import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ProjectNotFoundException;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.repository.FeatureRepository;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {
    private FeatureRepository _featureRepository;

    private static final Logger LOG = LoggerFactory.getLogger(FeatureService.class);


    @Autowired
    public FeatureService(FeatureRepository featureRepository) {

        Validate.notNull(featureRepository, "featureRepository cannot be null");

        _featureRepository = featureRepository;
    }

    public List<Feature> fetchFeatures(String projectName) throws FeatureNotFoundException, ProjectNotFoundException {

        Validate.notEmpty(projectName, "projectName cannot be null");

        return _featureRepository.fetchFeatures(projectName);

    }

    public Optional<Feature> getFeatureName(String projectName, String featureId) throws FeatureNotFoundException {

        Validate.notEmpty(featureId, "featureId cannot be empty or null");

        Optional<Feature> feature = _featureRepository.getFeatureName(projectName, featureId);

        if (feature.isPresent()) {

            LOG.error("Feature found by id '{}'", featureId);
            return feature;

        } else {

            LOG.error("Feature not found by id '{}'", featureId);
            throw new FeatureNotFoundException("Feature '" + featureId + "' not found");
        }
    }

}
