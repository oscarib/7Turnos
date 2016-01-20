package es.edm.controllers;

import java.util.ArrayList;
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

import es.edm.exceptions.DDBBException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnException;
import es.edm.model.JSPSimpleTurn;
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
	@SuppressWarnings("unchecked")
	@RequestMapping(path="/showTurns", method=RequestMethod.POST)
	public ModelAndView searchTurns(SimpleTurn turnSearched, Errors errors) throws DDBBException, TurnException {
		//Validate that form entries
		searchingTurnvalidator.validate(turnSearched, errors);

		//Gets the backing Object form the Form
		response.put("simpleTurn", turnSearched);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/showTurns.jsp", "response", response);
		}
		
		//UID
		/* If UID was specified, there is no need to mix no list at all, 
		 * as it should be returned just one turn
		 */
		if (turnSearched.getUid()!=0){
			List<SimpleTurn> turns = new ArrayList<SimpleTurn>();
			try {
				SimpleTurn turn = main.getTurnByID(turnSearched.getUid());
				turns.add(turn);
				response.put("turns", getJSPSimpleTurns(turns));
				return new ModelAndView("/web/showTurns.jsp", "response", response);
			} catch (TurnException e) {
			}
		}
		
		//PRAYER_ID
		/* If prayer_id was provided, take the turns and render the result without
		 * having to process any more fields, as there are no need to do
		 */
		if (turnSearched.getPrayer_id()!=0){
			try {
				List<SimpleTurn> turns = main.getPrayerTurns(turnSearched.getPrayer_id());
				response.put("turns", getJSPSimpleTurns(turns));
				return new ModelAndView("/web/showTurns.jsp", "response", response);
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//DOW
		if (turnSearched.getDow()!=null){
			//TODO: Change getAllTurns to include ordering by at least turn
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				if (nextTurn.getDow().equals(turnSearched.getDow())){
					dowTurns.add(new JSPSimpleTurn(nextTurn));
				}
			}
			response.put("turns", dowTurns);
		}

		//STATUS
		if (turnSearched.getStatus()!=null){
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				if (nextTurn.getStatus().toString().equals(turnSearched.getStatus().toString())){
					dowTurns.add(new JSPSimpleTurn(nextTurn));
				}
			}
			mixOfLists(dowTurns);
		}

		//NOTES
		if (turnSearched.getNotes()!=null && !turnSearched.getNotes().isEmpty()){
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				if (nextTurn.getNotes()!=null){
					if (nextTurn.getNotes().contains(turnSearched.getNotes())){
						dowTurns.add(new JSPSimpleTurn(nextTurn));
					}
				}
			}
			mixOfLists(dowTurns);
		}

		response.put("turnsSize", ((ArrayList<SimpleTurn>)response.get("turns")).size());
		return new ModelAndView("/web/showTurns.jsp", "response", response);
	}
	
	/* Takes a list of simple turns, and convert each turn in the list to 
	 * JSPSimpleTurn, which is in a compatible format for showing on the
	 * rendered view
	 */
	private List<JSPSimpleTurn> getJSPSimpleTurns(List<SimpleTurn> turns) throws TurnException{
		List<JSPSimpleTurn> resultListOfTurns = new ArrayList<JSPSimpleTurn>();
		for (SimpleTurn nextTurn : turns){
			resultListOfTurns.add(new JSPSimpleTurn(nextTurn));
		}
		return resultListOfTurns;
	}
	
	/* Makes a mix of the actual List of Turns to be displayed, and 
	 * the new one, in order to see the matches
	 */
	private void mixOfLists(List<JSPSimpleTurn> newList){
		//Take the previous list of prayers already set on the response
		@SuppressWarnings("unchecked")
		List<JSPSimpleTurn> previousList = (List<JSPSimpleTurn>)response.get("turns");
		//mix the two lists (and method)
		List<JSPSimpleTurn> mixedListOfTurns = new ArrayList<JSPSimpleTurn>();
		for (JSPSimpleTurn nextTurn : previousList){
			if (newList.contains(nextTurn)){
				mixedListOfTurns.add(nextTurn);
			}
		}
		//put the new mixed list on the response
		response.put("turns", mixedListOfTurns);
	}
}
