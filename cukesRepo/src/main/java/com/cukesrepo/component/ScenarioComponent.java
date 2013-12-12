package com.cukesrepo.component;


import com.cukesrepo.domain.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScenarioComponent {

    public List<Scenario> updateScenarios(List<Scenario> approvedScenarios, List<Scenario> gitScenarios) {

        for (Scenario approvedScenario : approvedScenarios) {
            for (Scenario gitScenario : gitScenarios) {
                if (approvedScenario.compareTo(gitScenario)) {

                    gitScenario.setApproved(approvedScenario.getApproved());

                    gitScenario.setComments(approvedScenario.getComments());
                }
            }
        }

        return gitScenarios;
    }
}
