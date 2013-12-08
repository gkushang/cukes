package com.cukesrepo.component;


import com.cukesrepo.Exceptions.FeatureNotFoundException;
import com.cukesrepo.Exceptions.ScenariosNotFoundException;
import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
import com.cukesrepo.domain.Scenario;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class GitComponent {
    private final String FEATURE_FILE_EXTENSION = ".feature";
    private final String _featureFilePath;


    private static final Logger LOG = LoggerFactory.getLogger(GitComponent.class);

    @Autowired
    public GitComponent
            (
                    @Value("${feature.file.path}") String featureFilePath
            ) {

        Validate.notEmpty(featureFilePath, "featureFilePath cannot be null or empty");

        _featureFilePath = featureFilePath;
    }

    public List<Feature> fetchFeatures(Project project) throws FeatureNotFoundException {
        //TODO - Replace by GitHub code

        String featureFileAbsolutePath = _getFeaturesAbsolutePath(project);

        List<Feature> features = new ArrayList<>();

        for (File file : _findAllFeatureFiles(featureFileAbsolutePath)) {
            Feature feature = _convertFeatureFileToPOJO(file.getAbsolutePath());

            feature.setProjectName(project.getName());

            features.add(feature);
        }

        if (features.size() > 0) LOG.info("Fetched '{}' feature(s) from Git/Local repository", features.size());

        else throw new FeatureNotFoundException("There are no Feature file available for '" +
                project.getName() + "' at path " + project.getRepositoryPath());

        return features;
    }


    public List<Scenario> fetchScenarios(Project project, String featureId) throws ScenariosNotFoundException {

        String featureFileAbsolutePath = _getFeaturesAbsolutePath(project);

        for (File file : _findAllFeatureFiles(featureFileAbsolutePath)) {
            Feature feature = _convertFeatureFileToPOJO(file.getAbsolutePath());

            if (feature.getId().equals(featureId)) {
                for (Scenario scenario : feature.getScenarios()) {
                    scenario.setFeatureId(featureId);
                    scenario.setProjectName(project.getName());
                    scenario.setFeatureName(feature.getName());
                }

                return feature.getScenarios();
            }
        }

        throw new ScenariosNotFoundException("There are no scenarios found for Project '" + project.getName() + "' and Feature Id '" + featureId + "'");
    }

    private String _getFeaturesAbsolutePath(Project project) {

        return project.getRepositoryPath() + _featureFilePath;
    }

    private File[] _findAllFeatureFiles(String directoryPath) {

        File dir = new File(directoryPath);

        return
                dir.listFiles
                        (new FilenameFilter() {
                            public boolean accept(File dir, String filename) {
                                return filename.endsWith(FEATURE_FILE_EXTENSION);
                            }
                        }
                        );
    }

    private Feature _convertFeatureFileToPOJO(String path) {

        try {
            String gherkin = FixJava.readReader(new InputStreamReader(
                    new FileInputStream(path), "UTF-8"));

            StringBuilder json = new StringBuilder();

            JSONFormatter formatter = new JSONFormatter(json);

            Parser parser = new Parser(formatter);

            parser.parse(gherkin, path, 0);

            formatter.done();

            formatter.close();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json.toString(), Feature[].class)[0];


        } catch (Exception e) {
            throw new RuntimeException("Error in parsing feature file to Json : " + path, e);
        }

    }


}
