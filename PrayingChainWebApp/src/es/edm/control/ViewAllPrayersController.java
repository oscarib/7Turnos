package es.edm.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.DDBBException;
import es.edm.model.Prayer;
import es.edm.services.MainService;

@Controller
public class ViewAllPrayersController {

	@Autowired
	private MainService main;
	
	@RequestMapping("/viewAllPrayers")
	public ModelAndView viewAllPrayers() throws DDBBException{
		List<Prayer> prayers = main.getAllPrayers();
		return new ModelAndView("/web/viewAllPrayers.jsp", "allPrayers", prayers);
	}
}
