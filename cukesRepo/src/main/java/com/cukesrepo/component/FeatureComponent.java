package com.cukesrepo.component;


import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.FeatureStatus;
import com.cukesrepo.domain.Project;
import org.springframework.stereotype.Component;

@Component
public class FeatureComponent {


    public float getPercentageOfApprovedScenarios(Project project, Feature feature, int totalScenarios) {

        if (feature.getTotalScenarios() != 0)
            return ((float) totalScenarios
                    /
                    (float) feature.getTotalScenarios()) * 100;
        else
            return 0;
    }

    public void updateFeatureAttributes(Project project, Feature gitFeature, Feature dbFeature, int totalScenarios) {

        float percentageApproved = Math.round(getPercentageOfApprovedScenarios(project, gitFeature, totalScenarios));

        gitFeature.setTotalApprovedScenarios(percentageApproved);

        if (percentageApproved >= 100) {
            gitFeature.setStatus(FeatureStatus.APPROVED.get());
        } else if (dbFeature.getEmailSent()) {
            gitFeature.setStatus(FeatureStatus.UNDER_REVIEW.get());
            gitFeature.setEmailSent(true);
        } else
            gitFeature.setStatus(percentageApproved <= 0 ? FeatureStatus.NEED_REVIEW.get() : FeatureStatus.UNDER_REVIEW.get());

    }
}
