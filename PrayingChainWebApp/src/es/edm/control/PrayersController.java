package es.edm.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.edm.services.MainService;

@Controller
public class PrayersController {

	@Autowired
	private MainService main;

	@RequestMapping("/showPrayers")
	public ModelAndView searchPrayers(String name, String email) {
		//TODO: Write Controller searchPrayers()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/deletePrayer")
	public ModelAndView deletePrayer(String prayerId){
		//TODO: Write Controller deletePrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/changePrayer")
	public ModelAndView changePrayer(String prayerID){
		//TODO: Write Controller changePrayer()
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
