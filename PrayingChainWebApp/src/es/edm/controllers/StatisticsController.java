package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatisticsController {

	@RequestMapping(path="/showStatistics", method=RequestMethod.GET)
	public ModelAndView showStatistics(){
		return new ModelAndView("/web/ShowStatistics.jsp");
	}

	@RequestMapping(path="/showStatistics", method=RequestMethod.POST)
	public void showStatistics(Errors errors){
		
	}
}
