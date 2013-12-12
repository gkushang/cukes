package com.cukesrepo.repository.feature;

import com.cukesrepo.component.GitComponent;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.FeatureStatus;
import com.cukesrepo.domain.Project;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ProjectNotFoundException;
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


        for (Feature feature : gitFeatures) {
            LOG.info("_scenarioRepository.getApprovedScenariosFromDB(project.getName(), feature.getId()).size() = {}", _scenarioRepository.getApprovedScenariosFromDB(project.getName(), feature.getId()).size());
            LOG.info("feature.getTotalScenarios() = {}", feature.getTotalScenarios());

            float percentageApproved = Math.round(_getPercentageOfApprovedScenarios(project, feature));

            feature.setTotalApprovedScenarios
                    (
                            percentageApproved
                    );

            if (percentageApproved >= 100)
                feature.setStatus(FeatureStatus.APPROVED.get());
            else if (feature.getEmailSent())
                feature.setStatus(FeatureStatus.UNDER_REVIEW.get());
            else
                feature.setStatus(percentageApproved <= 0 ? FeatureStatus.NEED_REVIEW.get() : FeatureStatus.UNDER_REVIEW.get());


        }
        _mongoTemplate.remove
                (
                        new Query(Criteria.where(Feature.PROJECTNAME).is(project.getName())),
                        Feature.class
                );

        LOG.info("Insert features to DB for Project '{}'", project.getName());

        _mongoTemplate.insertAll(gitFeatures);

        return gitFeatures;

    }

    private float _getPercentageOfApprovedScenarios(Project project, Feature feature) {

        if (feature.getTotalScenarios() != 0)
            return ((float) _scenarioRepository.getApprovedScenariosFromDB(project.getName(), feature.getId()).size()
                    /
                    (float) feature.getTotalScenarios()) * 100;
        else
            return 0;
    }

    public Optional<Feature> getFeatureById(String projectName, String id) {

        Query query = new Query((Criteria.where(Feature.ID).is(id)).and(Feature.PROJECTNAME).is(projectName));

        LOG.info("Get feature name for featureId '{}' and Project '{}'", id, projectName);

        return Optional.fromNullable(_mongoTemplate.findOne(query, Feature.class));
    }

    @Override
    public void setEmailSentAndStatus(String projectName, String featureId) throws FeatureNotFoundException, ProjectNotFoundException {

        Optional<Feature> featureOptional = getFeatureById(projectName, featureId);

        if (featureOptional.isPresent()) {
            Feature feature = featureOptional.get();
            feature.setStatus(FeatureStatus.UNDER_REVIEW.get());
            feature.setEmailSent(true);

        } else
            throw new FeatureNotFoundException("Feature " + featureId + " was not found");

    }

}