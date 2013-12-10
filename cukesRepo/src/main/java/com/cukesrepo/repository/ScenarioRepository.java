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


    public void insertScenarios(String projectName, String featureId, List<Scenario> gitScenarios) {

        List<Scenario> approvedScenarios = getApprovedScenariosFromDB(projectName, featureId);

        _mongoTemplate.remove(_queryToFindAllScenarios(projectName, featureId), Scenario.class);

        _mongoTemplate.insertAll(approveScenarios(approvedScenarios, gitScenarios));
    }

    public List<Scenario> approveScenarios(List<Scenario> approvedScenarios, List<Scenario> gitScenarios)
    {
        for(Scenario approvedScenario : approvedScenarios)
            for(Scenario gitScenario : gitScenarios)
                if(approvedScenario.compareTo(gitScenario))
                    gitScenario.setApproved(true);

        return gitScenarios;
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

    public void approveScenario(String projectName, String featureId, String scenarioNumber) throws ScenariosNotFoundException {

        Optional<Scenario> scenario = _findOneScenarioByNumber(projectName, featureId, scenarioNumber);

        if (scenario.isPresent()) {

            scenario.get().setApproved(true);

            _mongoTemplate.save(scenario.get());

        } else {
            throw new ScenariosNotFoundException("Sorry, we couldn't find a scenario");
        }
    }


    private Optional<Scenario> _findOneScenarioByNumber(String projectName, String featureId, String scenarioNumber) {

        Optional<Scenario> scenarioOptional = Optional.fromNullable(_mongoTemplate.findOne
                (
                        _queryToFindOneScenarioByNumber(projectName, featureId, scenarioNumber),
                        Scenario.class
                ));

        if (!scenarioOptional.isPresent())
            LOG.warn("Scenario by number '{}' not found for Project '{}' and FeatureID '{}'", scenarioNumber, projectName, featureId);

        else
            LOG.info("Scenario by number '{}' found for Project '{}' and FeatureID '{}'", scenarioNumber, projectName, featureId);

        return scenarioOptional;

    }

    private Query _queryToFindOneScenarioByNumber(String projectName, String featureId, String scenarioNumber) {

        return new Query
                (
                        Criteria.
                                where(Scenario.PROJECTNAME).
                                is(projectName).

                                and(Scenario.FEATUREID).
                                is(featureId).

                                and(Scenario.NUMBER).
                                is(Integer.parseInt(scenarioNumber))
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
