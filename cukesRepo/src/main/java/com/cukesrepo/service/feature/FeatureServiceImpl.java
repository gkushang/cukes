package com.cukesrepo.service.feature;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.repository.feature.FeatureRepository;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private FeatureRepository _featureRepository;

    private static final Logger LOG = LoggerFactory.getLogger(FeatureService.class);

    @Autowired
    public FeatureServiceImpl
            (
                    FeatureRepository featureRepository
            ) {

        Validate.notNull(featureRepository, "featureRepository cannot be null");

        _featureRepository = featureRepository;
    }

    public List<Feature> fetchFeatures(Project project) throws FeatureNotFoundException, ProjectNotFoundException {

        Validate.notNull(project, "project cannot be null");

        return _featureRepository.fetchFeatures(project);

    }

    public Optional<Feature> getFeatureName(String projectName, String featureId) throws FeatureNotFoundException {

        Validate.notEmpty(featureId, "featureId cannot be empty or null");

        Optional<Feature> feature = _featureRepository.getFeatureById(projectName, featureId);

        if (feature.isPresent()) {

            LOG.error("Feature found by id '{}'", featureId);
            return feature;

        } else {

            LOG.error("Feature not found by id '{}'", featureId);
            throw new FeatureNotFoundException("Feature '" + featureId + "' not found");
        }
    }

    @Override
    public void setEmailSent(String projectName, String featureId) throws FeatureNotFoundException, ProjectNotFoundException {

        _featureRepository.setEmailSentAndStatus(projectName, featureId);

    }

}
