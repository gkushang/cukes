package com.cukesrepo.component;


import com.cukesrepo.domain.Feature;
import com.cukesrepo.domain.Project;
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
public class GitComponent
{
    private final String FEATURE_FILE_EXTENSION = ".feature";
    private final String _featureFilePath;
    private final FeatureComponent _featureComponent;

    private static final Logger LOG = LoggerFactory.getLogger(GitComponent.class);

    @Autowired
    public GitComponent
            (
                    @Value("${feature.file.path}") String featureFilePath,
                    FeatureComponent featureComponent
            )
    {
        Validate.notEmpty(featureFilePath, "featureFilePath cannot be null or empty");

        _featureFilePath = featureFilePath;
        _featureComponent = featureComponent;
    }

    public List<Feature> fetch(Project project)
    {
        //TODO - Replace by GitHub code

        String featureFileAbsolutePath = project.getRepositoryPath() + _featureFilePath;

        List<Feature> features = new ArrayList<>();

        for(File file : _finder(featureFileAbsolutePath))
        {
            Feature feature = _convertFeatureFileToPOJO(file.getAbsolutePath());

            features.add(_featureComponent.processFeature(project, feature));

        }

        LOG.info("Fetched '{}' feature(s) from Git/Local repository", features.size());

        return features;
    }

    private File[] _finder(String directoryPath)
    {
        File dir = new File(directoryPath);

        return
                dir.listFiles
                        (new FilenameFilter()
                        {
                            public boolean accept(File dir, String filename)
                            {
                                return filename.endsWith(FEATURE_FILE_EXTENSION);
                            }
                        }
                        );
    }

    private Feature _convertFeatureFileToPOJO(String path)
    {

        try
        {
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


        } catch (Exception e)
        {
            throw new RuntimeException("\nError in parsing feature file to Json : " + path, e);
        }

    }

}
