package com.cukesrepo.controller;

import com.cukesrepo.domain.Project;
import com.cukesrepo.page.FeaturesPage;
import com.cukesrepo.page.ProjectsPage;
import com.cukesrepo.service.FeatureService;
import org.apache.commons.lang.Validate;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.rendersnake.HtmlAttributesFactory.for_;

@Controller
public class FeaturesPageController {

    public FeatureService _featureService;


    @Autowired
    public FeaturesPageController(FeatureService featureService) {
        Validate.notNull(featureService, "featureService cannot be null");

        _featureService = featureService;

    }

//    @RequestMapping(value = {"/projects/{projectName}/"}, method = RequestMethod.GET)
//    protected ModelAndView featuresPage
//            (
//                    @PathVariable String projectName
//            ) {
//
//        ModelAndView model = new ModelAndView("FeaturesPage");
//
//        model.addObject("features", _featureService.fetch(projectName));
//
//        return model;
//    }

    @RequestMapping(value = {"/projects/{projectName}/"})
    @ResponseBody
    public void featurespage(HtmlCanvas html, @PathVariable String projectName) throws IOException {
        html.render(new SiteLayoutWrapper(new FeaturesPage(_featureService,projectName)));


    }


}