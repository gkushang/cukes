package com.cukesrepo.controller;

import com.cukesrepo.service.CukeService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.TreeSet;

@Controller
public class HomePageController  {


    private final CukeService _cukeService;

    @Autowired
    public HomePageController(CukeService cukeService)
    {
        Validate.notNull(cukeService, "cukeService cannot be null");
        _cukeService = cukeService;
    }

    @RequestMapping(value = "/{project}/home", method = RequestMethod.GET)
    protected ModelAndView homePage
    (
            @PathVariable String project,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {

        ModelAndView model = new ModelAndView("HomePage");

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> urls = new ArrayList<String>();
        TreeSet<FeatureFile> features =  _cukeService.readFeatures();

        for(FeatureFile feature : features)
        {
            names.add(feature.getName());
            urls.add("features/"+ feature.getEndPoint());
        }

        model.addObject("featureFileNames", names);
        model.addObject("featureFileUrls", urls);

        return model;
    }

    @RequestMapping(value = "/home/upload", method = RequestMethod.GET)
    protected ModelAndView uploadHomePage(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception
    {
        ModelAndView model = new ModelAndView("HomePage");
        model.addObject("msg", "upload page");

        return model;
    }

}