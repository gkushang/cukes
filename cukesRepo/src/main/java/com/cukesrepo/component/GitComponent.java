package com.cukesrepo.component;


import com.cukesrepo.domain.*;
import com.cukesrepo.exceptions.FeatureNotFoundException;
import com.cukesrepo.exceptions.ScenariosNotFoundException;
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

        LOG.info("Fetching features from Git/Local repository for the project '{}'", project.getName());

        String featureFileAbsolutePath = _getFeaturesAbsolutePath(project);

        List<Feature> features = new ArrayList<>();

        for (FeatureFile featureFile : _findAllFeatureFiles(featureFileAbsolutePath)) {

            Feature feature = _convertFeatureFileToPOJO(featureFile.getFile().getAbsolutePath());

            feature.setProjectName(project.getName());

            feature.setTotalScenarios(getTotalScenarios(feature));

            feature.setDirectoryName(featureFile.getDirectoryName());

            features.add(feature);

        }

        if (features.size() > 0) LOG.info("Fetched '{}' feature(s) from Git/Local repository", features.size());

        else throw new FeatureNotFoundException("There are no Feature file available for '" +
                project.getName() + "' at path " + project.getRepositoryPath());

        return features;
    }


    public int getTotalScenarios(Feature feature) {

        int scenarioCount = 0;

        for (Scenario scenario : feature.getScenarios()) {
            for (Example example : scenario.getExamples()) {
                scenarioCount += example.getRows().size() - 2;
            }

            scenarioCount++;
        }
        return scenarioCount;
    }

    public List<Scenario> fetchScenarios(Project project, String featureId) throws ScenariosNotFoundException {

        LOG.info("Fetching scenarios from Git for the project '{}' and Feature '{}'", project.getName(), featureId);

        String featureFileAbsolutePath = _getFeaturesAbsolutePath(project);

        for (FeatureFile featureFile : _findAllFeatureFiles(featureFileAbsolutePath)) {
            Feature feature = _convertFeatureFileToPOJO(featureFile.getFile().getAbsolutePath());

            if (feature.getId().equals(featureId)) {
                int scenarioId = 0;

                for (Scenario scenario : feature.getScenarios()) {
                    scenario.setFeatureId(featureId);
                    scenario.setProjectName(project.getName());
                    scenario.setFeatureName(feature.getName());
                    scenario.setNumber(++scenarioId);
                }

                return feature.getScenarios();
            }
        }

        throw new ScenariosNotFoundException("There are no scenarios found for Project '" + project.getName() + "' and Feature Id '" + featureId + "'");
    }

    private String _getFeaturesAbsolutePath(Project project) {

        return project.getRepositoryPath() + _featureFilePath;
    }
//
//    private File[] _findAllFeatureFiles(String directoryPath) {
//
//        File dir = new File(directoryPath);
//
//        return
//                dir.listFiles
//                        (new FilenameFilter() {
//                            public boolean accept(File dir, String filename) {
//                                return filename.endsWith(FEATURE_FILE_EXTENSION);
//                            }
//                        }
//                        );
//    }

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

    private List<FeatureFile> _findAllFeatureFiles(String directoryPath) {

        List<FeatureFile> featureFiles = new ArrayList<>();

        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            search(directory, featureFiles);
        }

        return featureFiles;
    }

    private void search(File file, List<FeatureFile> featureFiles) {

        String directoryName;

        if (file.isDirectory()) {
            LOG.info("Searching feature files in directory '{}'", file.getAbsolutePath());
            directoryName = file.getName();

            if (file.canRead()) {

                for (File rFile : file.listFiles()) {
                    if (rFile.isDirectory()) {
                        search(rFile, featureFiles);
                    } else {
                        if (rFile.getName().endsWith(FEATURE_FILE_EXTENSION)) {
                            featureFiles.add(new FeatureFile(rFile, directoryName));
                        }
                    }
                }

            } else {
                LOG.warn("You do not have permission to access the directory '{}'", file.getAbsolutePath());
            }
        }

    }


}
