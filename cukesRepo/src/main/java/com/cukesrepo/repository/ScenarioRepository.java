package com.cukesrepo.repository;

import com.cukesrepo.Exceptions.ProjectNotFoundException;
import com.cukesrepo.Exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Scenario;
import com.google.common.base.Optional;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScenarioRepository {

    private final MongoTemplate _mongoTemplate;

    private final GitRepository _gitRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ScenarioRepository.class);

    @Autowired
    public ScenarioRepository
            (
                    GitRepository gitRepository,
                    MongoTemplate mongoTemplate
            ) {

        Validate.notNull(gitRepository, "gitRepository cannot be null");
        Validate.notNull(mongoTemplate, "mongoTemplate cannot be null");

        _gitRepository = gitRepository;
        _mongoTemplate = mongoTemplate;
    }


    public void insertScenarios(String projectName, String featureId, List<Scenario> scenarios) {

        List<Scenario> approvedScenarios = getApprovedScenariosFromDB(projectName, featureId);

        _mongoTemplate.remove(_queryToFindAllScenarios(projectName, featureId), Scenario.class);

        _mongoTemplate.insertAll(scenarios);

        processScenariosForApproval(approvedScenarios);
    }

    public void processScenariosForApproval(List<Scenario> approvedScenarios) {

        for (Scenario approvedScenario : approvedScenarios) {

            Optional<Scenario> gitScenario = _findOneScenarioById(approvedScenario.getProjectName(), approvedScenario.getFeatureId(), approvedScenario.getId());

            if (gitScenario.isPresent())
                updateScenarioIfApproved(approvedScenario, gitScenario.get());
        }
    }

    public void updateScenarioIfApproved(Scenario approvedScenario, Scenario gitScenario) {

        LOG.info("Found approved scenario '{}' for FeatureID '{}'", approvedScenario.getName(), approvedScenario.getFeatureId());

        _mongoTemplate.updateFirst
                (
                        _queryToFindOneScenarioById(approvedScenario.getProjectName(), approvedScenario.getFeatureId(), approvedScenario.getId()),
                        Update.update(Scenario.APPROVED, gitScenario.compareTo(approvedScenario)),
                        Scenario.class
                );

    }

    public List<Scenario> getApprovedScenariosFromDB(String projectName, String featureId) {

        Query query = new Query
                (
                        Criteria.
                                where(Scenario.PROJECTNAME).
                                is(projectName).
                                and(Scenario.FEATUREID).
                                is(featureId).
                                and(Scenario.APPROVED).is(true)
                );

        return _mongoTemplate.find(query, Scenario.class);

    }

    public List<Scenario> fetchScenarios(String projectName, String featureId) throws ProjectNotFoundException, ScenariosNotFoundException {

        List<Scenario> gitScenarios = _gitRepository.fetchScenarios(projectName, featureId);

        insertScenarios(projectName, featureId, gitScenarios);

        return _mongoTemplate.find(_queryToFindAllScenarios(projectName, featureId), Scenario.class);

    }

    public void approveScenario(String projectName, String featureId, String scenarioName) throws ScenariosNotFoundException {

        Optional<Scenario> scenario = _findOneScenarioByName(projectName, featureId, scenarioName);

        if (scenario.isPresent()) {

            scenario.get().setApproved(true);

            _mongoTemplate.save(scenario.get());
        } else {
            throw new ScenariosNotFoundException("Sorry, we couldn't find a scenario");
        }
    }

    private Optional<Scenario> _findOneScenarioById(String projectName, String featureId, String scenarioId) {

        Optional<Scenario> scenarioOptional = Optional.fromNullable(_mongoTemplate.findOne
                (
                        _queryToFindOneScenarioById(projectName, featureId, scenarioId),
                        Scenario.class
                ));

        if (!scenarioOptional.isPresent())
            LOG.warn("Scenario by ID '{}' not found for Project '{}' and FeatureID '{}'", scenarioId, projectName, featureId);

        else
            LOG.info("Scenario by ID '{}' found for Project '{}' and FeatureID '{}'", scenarioId, projectName, featureId);

        return scenarioOptional;

    }

    private Optional<Scenario> _findOneScenarioByName(String projectName, String featureId, String scenarioName) {

        Optional<Scenario> scenarioOptional = Optional.fromNullable(_mongoTemplate.findOne
                (
                        _queryToFindOneScenarioByName(projectName, featureId, scenarioName),
                        Scenario.class
                ));

        if (!scenarioOptional.isPresent())
            LOG.warn("Scenario by name '{}' not found for Project '{}' and FeatureID '{}'", scenarioName, projectName, featureId);

        else
            LOG.info("Scenario by name '{}' found for Project '{}' and FeatureID '{}'", scenarioName, projectName, featureId);

        return scenarioOptional;

    }

    private Query _queryToFindOneScenarioById(String projectName, String featureId, String scenarioId) {

        return new Query
                (
                        Criteria.
                                where(Scenario.PROJECTNAME).
                                is(projectName).

                                and(Scenario.FEATUREID).
                                is(featureId).

                                and(Scenario.ID).
                                is(scenarioId)
                );

    }

    private Query _queryToFindOneScenarioByName(String projectName, String featureId, String scenarioName) {

        return new Query
                (
                        Criteria.
                                where(Scenario.PROJECTNAME).
                                is(projectName).

                                and(Scenario.FEATUREID).
                                is(featureId).

                                and(Scenario.NAME).
                                is(scenarioName)
                );

    }

    private Query _queryToFindAllScenarios(String projectName, String featureId) {

        return new Query
                (
                        Criteria.
                                where(Scenario.PROJECTNAME).
                                is(projectName).
                                and(Scenario.FEATUREID).
                                is(featureId)
                );
    }
}
