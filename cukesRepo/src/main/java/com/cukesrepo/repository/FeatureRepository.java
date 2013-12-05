package com.cukesrepo.repository;

import com.cukesrepo.component.FeatureComponent;
import com.cukesrepo.domain.Feature;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class FeatureRepository
{

    private final String FEATURE_FILE_PATH = "/src/test/resources/features";
    private final ProjectRepository _projectRepository;
    private ArrayList<Feature> _features = new ArrayList<Feature>();
    private FeatureComponent _featureComponent;

    @Autowired
    public FeatureRepository(FeatureComponent featureComponent
    , ProjectRepository projectRepository)
    {

        Validate.notNull(featureComponent, "featureComponent cannot be null.");
        Validate.notNull(projectRepository, "projectRepository cannot be null.");

        _featureComponent = featureComponent;

        _projectRepository = projectRepository;

    }

    public ArrayList<Feature>  getFeatures()
    {
        return _features;
    }


    public ArrayList<Feature> fetch(String projectName)
    {
        _features = _featureComponent.fetch(_projectRepository.getProjectByName(projectName).get().getRepositoryPath() + FEATURE_FILE_PATH);

        return _features;
    }

}
