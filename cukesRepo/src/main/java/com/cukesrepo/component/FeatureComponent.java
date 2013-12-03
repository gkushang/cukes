package com.cukesrepo.component;


import com.cukesrepo.domain.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.TreeSet;

@Component
public class FeatureComponent
{
    private TreeSet<Feature[]> features = new TreeSet<Feature[]>();
    private String _name;

    public FeatureComponent()
    {

    }


    public TreeSet<Feature[]> readFeatures(String directoryPath)
    {
        for(File file : _finder(directoryPath))
        {
            String scenarioText = _read(file.getAbsolutePath());
            features.add(new FeatureFile(_name, scenarioText));
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
                                return filename.endsWith(".feature");
                            }
                        }
                        );
    }

    private String _read(String directoryPath) {

        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(directoryPath));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("<br>");
                _readFeatureTitle(line);
            }

            return sb.toString();

        }catch (Exception e)
        {
            throw new RuntimeException("Error Finding/Reading File : " + directoryPath, e);

        } finally
        {
            try
            {
                br.close();

            } catch (Exception e)
            {
                throw new RuntimeException("Error Closing File : " + directoryPath, e);
            }
        }
    }

    private void _readFeatureTitle(String line)
    {
        if(line.contains("Feature:"))
            _name = line.split("Feature:")[1].trim();

    }

    private void _parseFeatureFile(String path)
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

            features.add(mapper.readValue(json.toString(), Feature[].class));


        } catch (Exception e)
        {
            throw new RuntimeException("\nError in parsing file : " + path, e);
        }

    }
}
