package es.edm.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.DDBBException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.model.Prayer;
import es.edm.services.MainService;

@Controller
public class PrayersController {

	@Autowired
	private MainService main;

	@RequestMapping("/mostrarOradores")
	public ModelAndView viewAllPrayers() throws DDBBException{
		List<Prayer> prayers = main.getAllPrayers();
		return new ModelAndView("/web/viewAllPrayers.jsp", "allPrayers", prayers);
	}

	@RequestMapping("/buscarOrador")
	public ModelAndView searchPrayers(String name, String email) {
		List<Prayer> prayers = null;
		if (email!=null && !email.equals("")){
			prayers = new ArrayList<Prayer>();
			try {
				prayers.add(main.getPrayerByEmail(email));
			} catch (PrayerNotFoundException e) {
			}
		} else {
			if (name!=null && !name.equals("")){
				try {
					prayers = main.getPrayersByName(name);
				} catch (PrayerNotFoundException e) {
				}
			}
		}

		return new ModelAndView("/web/findPrayer.jsp", "prayers", prayers);
	}
}
