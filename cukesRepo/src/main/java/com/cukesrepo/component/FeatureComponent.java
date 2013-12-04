package com.cukesrepo.component;


import com.cukesrepo.domain.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
public class FeatureComponent
{
    private ArrayList<Feature> features = new ArrayList<Feature>();
    private final String FEATURE_FILE_EXTENSION = ".feature";

    public ArrayList<Feature> fetch(String directoryPath)
    {
        features.clear();
        for(File file : _finder(directoryPath))
        {
            features.add(_parseFeatureFile(file.getAbsolutePath()));
            _parseFeatureFile(file.getAbsolutePath());
        }

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

    private Feature _parseFeatureFile(String path)
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
            throw new RuntimeException("\nError in parsing file : " + path, e);
        }

    }
}
