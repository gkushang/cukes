package com.cukesrepo.component;

import com.cukesrepo.domain.Example;
import com.cukesrepo.domain.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScenarioComponent {

    private int _numberOfScenarios;
    private int _numberOfApprovedScenarios;

    public int getNumberOfApprovedScenarios() {
        return _numberOfApprovedScenarios;
    }

    public int getNumberOfScenarios() {
        return _numberOfScenarios;
    }

    public void processScenarios(List<Scenario> scenarios) {
        _numberOfScenarios = 0;
        _numberOfApprovedScenarios = 0;

        for (Scenario scenario : scenarios) {
            _numberOfScenarios++;

            for (Example example : scenario.getExamples())
                _numberOfScenarios += example.getRows().size() - 2;

            if (scenario.isApproved())
                _numberOfApprovedScenarios++;
        }

    }

}
