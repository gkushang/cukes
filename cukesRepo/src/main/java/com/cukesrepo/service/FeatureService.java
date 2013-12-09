package com.cukesrepo.service;

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

    public List<Feature> fetch(String projectName) {

        Validate.notEmpty(projectName, "projectName cannot be null");

        return _featureRepository.fetch(projectName);

    }

    public Optional<Feature> getFeatureById(String projectName, String featureId) {

        Validate.notEmpty(featureId, "featureId cannot be empty or null");

        Optional<Feature> feature = _featureRepository.getFeatureById(projectName, featureId);

        if (feature.isPresent()) {
            LOG.error("Feature found by id '{}'", featureId);
            return feature;
        } else LOG.error("Feature not found by id '{}'", featureId);

        return Optional.absent();
    }

    public void approveScenario(String projectName, String featureId, String scenarioId) {
        //TODO - Approve scenario

    }

}
