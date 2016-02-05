package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="/Forbidden")
public class ForbiddenController {

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showPageGet(){
		return new ModelAndView("/web/Forbidden.jsp");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView showPagePost(){
		return new ModelAndView("/web/Forbidden.jsp");
	}
	
}
