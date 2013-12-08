package com.cukesrepo.repository;

import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ProjectNotFoundException;
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

import java.util.List;

@Repository
public class FeatureRepository {

    private final MongoTemplate _mongoTemplate;
    private final GitRepository _gitRepository;

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

    public List<Feature> fetchFeatures(String projectName) throws FeatureNotFoundException, ProjectNotFoundException {

        LOG.info("Fetch features for Project '{}'", projectName);

        List<Feature> gitFeatures = _gitRepository.fetchFeatures(projectName);

        for (Feature feature : gitFeatures) feature.setProjectName(projectName);

        _mongoTemplate.remove(_queryToFindAllFeatures(projectName), Feature.class);

        LOG.info("Insert features to DB for Project '{}'", projectName);

        _mongoTemplate.insertAll(gitFeatures);

        return gitFeatures;

    }

    private Query _queryToFindAllFeatures(String projectName) {

        return new Query(Criteria.where(Feature.PROJECTNAME).is(projectName));
    }

    public Optional<Feature> getFeatureName(String projectName, String id) {

        Query query = new Query((Criteria.where(Feature.ID).is(id)).and(Feature.PROJECTNAME).is(projectName));

        return Optional.fromNullable(_mongoTemplate.findOne(query, Feature.class));
    }
}
