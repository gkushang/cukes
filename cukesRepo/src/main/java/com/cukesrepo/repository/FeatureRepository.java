package com.cukesrepo.repository;

import com.cukesrepo.component.FeatureComponent;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.TreeSet;

@Repository
public class FeatureRepository
{

    private final String FEATURE_FILE_PATH = "/src/test/resources/features";
    private final String _directoryPath;
    private TreeSet<FeatureFile> _features = new TreeSet<FeatureFile>();
    private FeatureComponent _featureComponent;

    @Autowired
    public FeatureRepository(@Value("${project.base.path}") String projectBasePath, FeatureComponent featureComponent)
    {
        Validate.notNull(projectBasePath, "projectBasePath cannot be null.");
        Validate.notNull(featureComponent, "featureComponent cannot be null.");

        _featureComponent = featureComponent;
        _directoryPath = projectBasePath + FEATURE_FILE_PATH;

    }

    public TreeSet<FeatureFile>  getFeatures()
    {
        return _features;
    }


    public FeatureFile getFeatureByEndPoint(String featureEndPoint)
    {

        for(FeatureFile feature: _features)
            if(feature.getEndPoint().equalsIgnoreCase(featureEndPoint)) return feature;

        return null;
    }

    public TreeSet<FeatureFile> readFeatures()
    {
        _features = _featureComponent.readFeatures(_directoryPath);
        return _features;
    }
}
