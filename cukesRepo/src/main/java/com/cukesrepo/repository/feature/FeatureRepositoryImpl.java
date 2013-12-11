package com.cukesrepo.repository.feature;

import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.component.GitComponent;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.repository.scenario.ScenarioRepository;
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
public class FeatureRepositoryImpl implements FeatureRepository {

    private final MongoTemplate _mongoTemplate;
    private final GitComponent _gitComponent;
    private final ScenarioRepository _scenarioRepository;

    private static final Logger LOG = LoggerFactory.getLogger(FeatureRepositoryImpl.class);

    @Autowired
    public FeatureRepositoryImpl
            (
                    GitComponent gitComponent,
                    ScenarioRepository scenarioRepository,
                    MongoTemplate mongoTemplate
            ) {

        Validate.notNull(gitComponent, "gitComponent cannot be null");
        Validate.notNull(scenarioRepository, "scenarioRepository cannot be null");
        Validate.notNull(mongoTemplate, "mongoTemplate cannot be null");

        _gitComponent = gitComponent;
        _scenarioRepository = scenarioRepository;
        _mongoTemplate = mongoTemplate;

    }

    public List<Feature> fetchFeatures(Project project) throws FeatureNotFoundException, ProjectNotFoundException {

        Validate.notNull(project, "project cannot be null");

        LOG.info("Fetch features for Project '{}'", project.getName());

        List<Feature> gitFeatures = _gitComponent.fetchFeatures(project);

        for (Feature feature : gitFeatures)
            feature.setTotalApprovedScenarios(_scenarioRepository.getApprovedScenariosFromDB(project.getName(), feature.getId()).size());

        _mongoTemplate.remove
                (
                        new Query(Criteria.where(Feature.PROJECTNAME).is(project.getName())),
                        Feature.class
                );

        LOG.info("Insert features to DB for Project '{}'", project.getName());

        _mongoTemplate.insertAll(gitFeatures);

        return gitFeatures;

    }

    public Optional<Feature> getFeatureName(String projectName, String id) {

        Query query = new Query((Criteria.where(Feature.ID).is(id)).and(Feature.PROJECTNAME).is(projectName));

        LOG.info("Get feature name for featureId '{}' and Project '{}'", id, projectName);

        return Optional.fromNullable(_mongoTemplate.findOne(query, Feature.class));
    }

}