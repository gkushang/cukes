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

@Controller
public class ScenariosPageController {


    private final CukeService _cukeService;

    @Autowired
    public ScenariosPageController(CukeService cukeService)
    {
        Validate.notNull(cukeService, "cukeService cannot be null");
        _cukeService = cukeService;
    }

    @RequestMapping(value = "/{project}/features/{featureEndPoint}", method = RequestMethod.GET)
	protected ModelAndView scenariosPage
    (
            @PathVariable String project,
            HttpServletRequest request,
            @PathVariable String featureEndPoint
    )
    {

        Validate.notNull(featureEndPoint, "featureEndPoint cannot be null");
		ModelAndView model = new ModelAndView("ScenarioPage");

		model.addObject("msg", _cukeService.getFeatureByEndPoint(featureEndPoint).getScenariosText());
		return model;
	}

}