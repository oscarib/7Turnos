package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping(path="/index", method=RequestMethod.GET)
	public ModelAndView showBootstrap(){
		return new ModelAndView("/web/index.jsp");
	}
}
