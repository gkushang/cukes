package com.cukesrepo.service;


import com.cukesrepo.Exceptions.ProjectNotFoundException;
import com.cukesrepo.Exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Scenario;
import com.cukesrepo.repository.ScenarioRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService {
    private final ScenarioRepository _scenarioRepository;

    @Autowired
    public ScenarioService(ScenarioRepository scenarioRepository) {

        Validate.notNull(scenarioRepository, "scenarioRepository cannot be null");

        _scenarioRepository = scenarioRepository;
    }


    public void approveScenario(String projectName, String featureId, String scenarioName) throws ScenariosNotFoundException {

        _scenarioRepository.approveScenario(projectName, featureId, scenarioName);

    }

    public List<Scenario> fetchScenarios(String projectName, String featureId) throws ScenariosNotFoundException, ProjectNotFoundException {
        return _scenarioRepository.fetchScenarios(projectName, featureId);
    }
}
