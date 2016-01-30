package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OtherControllers {

	@RequestMapping(path="/deletePrayer")
	public ModelAndView deletePrayer(String prayerId){
		//TODO: Write Controller deletePrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
		
	@RequestMapping(path="/addPrayer")
	public ModelAndView addPrayer(){
		//TODO: Write Controller addPrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping(path="/addPrayer2Turn")
	public ModelAndView addPrayer2Turn(){
		//TODO: Write Controller addPrayer2Turn()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
}
