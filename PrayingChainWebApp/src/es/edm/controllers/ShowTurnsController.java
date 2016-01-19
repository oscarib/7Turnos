package es.edm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.model.SimpleTurn;
import es.edm.services.MainService;
import es.edm.validators.SearchingTurnValidator;

@Controller
public class ShowTurnsController {

	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingTurnValidator searchingTurnvalidator;
	
	@Autowired
	ResourceBundleMessageSource messages;
	
	//For saving data to show on the View (JSP)
	private Map<String, Object> response;
	
	public ShowTurnsController(){
		//To hold all the data that should be returned back to the view
		response = new HashMap<String, Object>();
	}

//CONTROLLER METHODS // CONTROLLER METHODS // CONTROLLER METHODS
	
	//Presents the form
	@RequestMapping(path="/showTurns", method=RequestMethod.GET)
	public ModelAndView searchTurns() {
		return new ModelAndView("/web/showTurns.jsp", "simpleTurn", new SimpleTurn());
	}
	
	//Processes the form
	@RequestMapping(path="/showTurns", method=RequestMethod.POST)
	public ModelAndView searchTurns(SimpleTurn turnSearched, Errors errors) {
		//Validate that form entries
		searchingTurnvalidator.validate(turnSearched, errors);

		//Gets the backing Object form the Form
		response.put("simpleTurn", turnSearched);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/showTurns.jsp", "response", response);
		}
		
		//PRAYER_ID
		/* If prayer_id was provided, take the turns
		 */
		if (turnSearched.getPrayer_id()!=0){
			try {
				List<SimpleTurn> turns = main.getPrayerTurns(turnSearched.getPrayer_id());
				response.put("turns", turns);
			} catch (PrayerNotFoundException e) {
			}
		}

		return new ModelAndView("/web/showTurns.jsp", "response", response);
	}
	
//	private void mixOfLists(List<SimpleTurn> newList){
//		//Take the previous list of prayers already set on the response
//		@SuppressWarnings("unchecked")
//		List<SimpleTurn> previousList = (List<SimpleTurn>)response.get("simpleTurn");
//		//mix the two lists (and method)
//		List<SimpleTurn> mixedListOfTurns;
//		try {
//			mixedListOfTurns = main.andMixingOfLists(previousList, newList);
//			//put the new mixed list on the response
//			response.put("prayers", mixedListOfPrayers);
//		} catch (EmptyParameterException e) {
//		}
//	}
}
