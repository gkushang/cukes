package com.cukesrepo.service.feature;

import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.google.common.base.Optional;

import java.util.List;


public interface FeatureService {

    public List<Feature> fetchFeatures(Project project) throws FeatureNotFoundException, ProjectNotFoundException;

    public Optional<Feature> getFeatureName(String projectName, String featureId) throws FeatureNotFoundException;
}
