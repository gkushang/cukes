package com.cukesrepo.component;

import com.cukesrepo.domain.Example;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Scenario;
import org.springframework.stereotype.Component;

@Component
public class ScenarioComponent
{

    public void approveScenario(Scenario scenario)
    {
        scenario.setIsApproved(true);
    }

    public int getNumberOfScenarios(Feature feature)
    {
        int numberOfScenarios = 0;

        for(Scenario scenario : feature.getScenarios())
        {
            numberOfScenarios++;

            for(Example example : scenario.getExamples())
                numberOfScenarios += example.getRows().size() - 2;
        }

        return numberOfScenarios;
    }

    public int getNumberOfApprovedScenarios(Feature feature)
    {

        int numberOfApprovedScenarios = 0;

        for(Scenario scenario : feature.getScenarios())
        {
            if(scenario.isApproved())
                numberOfApprovedScenarios++;
        }

        return numberOfApprovedScenarios;

    }
}
