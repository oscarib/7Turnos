package es.edm.control;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.DDBBException;
import es.edm.model.Prayer;
import es.edm.services.MainService;

public class ViewAllPrayersController {

	private MainService main;

	public void setMainService(MainService main) {
		this.main = main;
	}
	
	@RequestMapping("/viewAllPrayers")
	public ModelAndView viewAllPrayers() throws DDBBException{
		List<Prayer> prayers = main.getAllPrayers();
		return new ModelAndView("/web/viewAllPrayers.jsp", "allPrayers", prayers);
	}
}
