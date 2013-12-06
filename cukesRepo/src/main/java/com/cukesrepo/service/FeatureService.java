package com.cukesrepo.service;

import com.cukesrepo.domain.Feature;
import com.cukesrepo.repository.FeatureRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;

@Service
public class FeatureService
{
    private FeatureRepository _featureRepository;
    private int _totalNumberOfScenarios = 0;
    private EmailService sendEmail;

    @Autowired
    public FeatureService(FeatureRepository featureRepository)
    {
        Validate.notNull(featureRepository, "featureRepository cannot be null.");
        _featureRepository = featureRepository;
    }

    public ArrayList<Feature> fetch(String projectName)
    {
        return _featureRepository.fetch(projectName);
    }

    public ArrayList<Feature> getFeatures()
    {
        return _featureRepository.getFeatures();
    }

    public Feature getFeatureById(String featureId)
    {

        for(Feature feature : _featureRepository.getFeatures())
        {
            if(feature.getId().equalsIgnoreCase(featureId))
                return feature;
        }

        return null;
    }

    public int getTotalNumberOfScenarios()
    {

        _totalNumberOfScenarios = 0;
        for(Feature feature : _featureRepository.getFeatures())
        {
            _totalNumberOfScenarios += feature.getNumberOfScenarios();
        }

        return _totalNumberOfScenarios;
    }
    public void sendEmail() throws MessagingException {

        sendEmail=new EmailService();
        sendEmail.send();
        }
}
