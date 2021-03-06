package com.cukesrepo.service.scenario;

import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Scenario;
import com.cukesrepo.repository.scenario.ScenarioRepository;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository _scenarioRepository;

    @Autowired
    public ScenarioServiceImpl
            (
                    ScenarioRepository scenarioRepository
            ) {

        Validate.notNull(scenarioRepository, "scenarioRepository cannot be null");

        _scenarioRepository = scenarioRepository;
    }

    public List<Scenario> fetchScenarios(Project project, String featureId) throws ScenariosNotFoundException, ProjectNotFoundException {

        return _scenarioRepository.fetchScenarios(project, featureId);
    }

    public void approveScenario(String projectName, String featureId, String scenarioNumber) throws ScenariosNotFoundException {

        _scenarioRepository.approveScenario(projectName, featureId, scenarioNumber);

    }

    public void addComment(String projectName, String featureId, String scenarioNumber, String comment) throws ScenariosNotFoundException {

        _scenarioRepository.addComment(projectName, featureId, scenarioNumber, comment);
    }
}
