package com.cukesrepo.repository;

import com.cukesrepo.domain.Feature;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeatureRepository {

    private final MongoTemplate _mongoTemplate;
    private final GitRepository _gitRepository;
    private List<Feature> _features = new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(FeatureRepository.class);

    @Autowired
    public FeatureRepository
            (
                    GitRepository gitRepository,
                    MongoTemplate mongoTemplate

            ) {

        Validate.notNull(gitRepository, "gitRepository cannot be null");
        Validate.notNull(mongoTemplate, "mongoTemplate cannot be null");

        _gitRepository = gitRepository;
        _mongoTemplate = mongoTemplate;

    }

    public List<Feature> getFeatures() {
        return _features;
    }


    public List<Feature> fetch(String projectName) {

        Query query = new Query(Criteria.where(Feature.PROJECTNAME).is(projectName));

        List<Feature> _features = _mongoTemplate.find(query, Feature.class);

        if (_features == null || _features.size() <= 0) {
            LOG.info("Fetching features from Git for '{}' project", projectName);

            _features = _gitRepository.fetchFeatures(projectName);

            LOG.info("Inserting features to db for '{}' project", projectName);

            _mongoTemplate.insertAll(_features);

        } else {
            LOG.info("Features found from db for '{}' project", projectName);
        }

        return _features;

    }

    public void approveScenario(String featureId, String scenarioId) {
        //TODO - add code for approval

    }

    public Optional<Feature> getFeatureById(String projectName, String id) {
        Query query = new Query((Criteria.where(Feature.ID).is(id)).and(Feature.PROJECTNAME).is(projectName));

        return Optional.fromNullable(_mongoTemplate.findOne(query, Feature.class));
    }
}
