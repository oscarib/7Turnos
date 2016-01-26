package es.edm.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.model.JSPPrayer;

@Controller
@RequestMapping(path="/createPrayer")
public class CreatePrayerController {
	
	private Map<String, Object> model;
	
	public CreatePrayerController(){
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showForm(){
		model = new LinkedHashMap<String, Object>();
		model.put("newPrayer", new JSPPrayer());
		return new ModelAndView("/web/createPrayer.jsp", model);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("newPrayer") JSPPrayer prayerSearched, Errors errors){
		model = new LinkedHashMap<String, Object>();
		model.put("newPrayer", new JSPPrayer());
		return new ModelAndView("/web/createPrayer.jsp", model);
	}
}
