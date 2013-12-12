package com.cukesrepo.repository.feature;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.google.common.base.Optional;

import java.util.List;


public interface FeatureRepository {

    public List<Feature> fetchFeatures(Project project) throws FeatureNotFoundException, ProjectNotFoundException;

    public Optional<Feature> getFeatureById(String projectName, String id) throws FeatureNotFoundException;

    public void setEmailSentAndStatus(String projectName, String featureId) throws FeatureNotFoundException, ProjectNotFoundException;
}
