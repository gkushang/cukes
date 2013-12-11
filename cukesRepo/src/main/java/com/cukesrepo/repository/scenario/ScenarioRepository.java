package com.cukesrepo.repository.scenario;

import com.cukesrepo.exceptions.ProjectNotFoundException;
import com.cukesrepo.exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Scenario;

import java.util.List;

public interface ScenarioRepository {

    public List<Scenario> fetchScenarios(Project project, String featureId) throws ProjectNotFoundException, ScenariosNotFoundException;

    public List<Scenario> getApprovedScenariosFromDB(String projectName, String featureId);

    public void approveScenario(String projectName, String featureId, String scenarioNumber) throws ScenariosNotFoundException;

    public void addComment(String projectName, String featureId, String scenarioNumber, String comment);
}
