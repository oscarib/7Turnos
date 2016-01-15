package es.edm.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.model.Prayer;
import es.edm.services.MainService;

@Controller
@RequestMapping("/nuevoOrador")
public class CreateNewPrayersController {

	@Autowired
	private MainService main;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("/web/createNewPrayer.jsp", "prayer", new Prayer());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processForm(Prayer newPrayer){
		return new ModelAndView("/web/prayerCreated.jsp", "title", newPrayer.getName());
	}
}
