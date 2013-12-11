package com.cukesrepo.service.scenario;


import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Scenario;

import java.util.List;


public interface ScenarioService {

    public List<Scenario> fetchScenarios(Project project, String featureId) throws ScenariosNotFoundException, ProjectNotFoundException;

    public void approveScenario(String projectName, String featureId, String scenarioNumber) throws ScenariosNotFoundException;

    public void addComment(String projectName, String featureId, String scenarioNumber, String comment) throws ScenariosNotFoundException;
}
