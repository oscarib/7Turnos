package es.edm.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(path="/showTurns")
public class ShowTurnsController {

	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingTurnValidator searchingTurnvalidator;
	
	@Autowired
	ResourceBundleMessageSource messages;
	
	//For saving data to show on the View (JSP)
	private Map<String, Object> response;
	
	//List of days
	private Map<String, String> dowList;
	
	//List of status
	private Map<String, String> statusList;
	
	//List of turns
	private Map<String, String> turnList;
	
	public ShowTurnsController(){
		resetForm();
	}

//CONTROLLER METHODS // CONTROLLER METHODS // CONTROLLER METHODS
	
	//Presents the form
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView searchTurns() {
		resetForm();
		return new ModelAndView("/web/showTurns.jsp", response);
	}
	
	//Processes the form
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView searchTurns(@ModelAttribute("simpleTurn") JSPSimpleTurn turnSearched, Errors errors) throws DDBBException, TurnException {
		//Validate that form entries
		searchingTurnvalidator.validate(turnSearched, errors);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			resetForm();
			return new ModelAndView("/web/showTurns.jsp", response);
		}
		
		//Reset response, in order to construct a new fresh one
		resetForm();
		
		//UID
		/* If UID was specified, there is no need to mix no list at all, 
		 * as it should be returned just one turn
		 */
		if (!turnSearched.getUid().equals("")){
			List<SimpleTurn> turns = new ArrayList<SimpleTurn>();
			try {
				SimpleTurn turn = main.getTurnByID(Integer.parseInt(turnSearched.getUid()));
				turns.add(turn);
				response.put("turns", getJSPSimpleTurns(turns));
				orderList();
				addColateralData();
				return new ModelAndView("/web/showTurns.jsp", response);
			} catch (TurnException e) {
			}
		}
		
		//PRAYER_ID
		/* If prayer_id was provided, take the turns and render the result without
		 * having to process any more fields, as there are no need to do
		 */
		if (!turnSearched.getPrayer_id().equals("")){
			try {
				List<SimpleTurn> turns = main.getPrayerTurns(Integer.parseInt(turnSearched.getPrayer_id()));
				response.put("turns", getJSPSimpleTurns(turns));
				addColateralData();
				orderList();
				return new ModelAndView("/web/showTurns.jsp", response);
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//DOW
		if (!turnSearched.getDow().equals("NotSelected")){
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				if (nextTurn.getDow().toString().equals(turnSearched.getDow())){
					dowTurns.add(new JSPSimpleTurn(nextTurn));
				}
			}
			mixOfLists(dowTurns);
		}

		//TURN
		if (!turnSearched.getTurn().equals("NotSelected")){
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				int turn2Int = Integer.parseInt(turnSearched.getTurn());
				if (nextTurn.getTurn() == turn2Int){
					dowTurns.add(new JSPSimpleTurn(nextTurn));
				}
			}
			mixOfLists(dowTurns);
		}

		//STATUS
		if (!turnSearched.getStatus().equals("NotSelected")){
			List<SimpleTurn> turns = main.getAllTurns();
			List<JSPSimpleTurn> dowTurns = new ArrayList<JSPSimpleTurn>();
			for (SimpleTurn nextTurn : turns){
				if (nextTurn.getStatus().toString().equals(turnSearched.getStatus())){
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
		
		addColateralData();
		
		//Adds orphan turns to the result
		return new ModelAndView("/web/showTurns.jsp", response);
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
	
	@SuppressWarnings("unchecked")
	private void addColateralData() throws TurnException{
		List<SimpleTurn> orphanTurns = main.getOrphanTurns();
		response.put("orphanTurns", getJSPSimpleTurns(orphanTurns));
		response.put("errorsSize", orphanTurns.size());
		response.put("turnsSize", ((ArrayList<JSPSimpleTurn>)response.get("turns")).size());
	}
	
	/* Makes a mix of the actual List of Turns to be displayed, and 
	 * the new one, in order to see the matches
	 */
	private void mixOfLists(List<JSPSimpleTurn> newList){
		//Take the previous list of prayers already set on the response
		@SuppressWarnings("unchecked")
		List<JSPSimpleTurn> previousList = (List<JSPSimpleTurn>)response.get("turns");
		if (previousList==null || previousList.size()==0) {
			response.put("turns", newList);
		} else {
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
		orderList();
	}
	
	@SuppressWarnings("unchecked")
	private void orderList(){
		Collections.sort((ArrayList<JSPSimpleTurn>)response.get("turns"), new SimpleTurnComparator());
	}

	private void resetForm(){
		//To hold all the data that should be returned back to the view
		response = new HashMap<String, Object>();
		response.put("simpleTurn", new JSPSimpleTurn());
		response.put("turns", new ArrayList<JSPSimpleTurn>());
		
		dowList = new LinkedHashMap<String, String>();
		dowList.put("NotSelected", "Filtrar por día");
		dowList.put("monday", "Lunes");
		dowList.put("tuesday", "Martes");
		dowList.put("wednesday", "Miércoles");
		dowList.put("thursday", "Jueves");
		dowList.put("friday", "Viernes");
		dowList.put("saturday", "Sábado");
		dowList.put("sunday", "Domingo");
		response.put("dowList", dowList);

		statusList = new LinkedHashMap<String, String>();
		statusList.put("NotSelected", "Filtrar por Estado");
		statusList.put("accepted", "Aceptado");
		statusList.put("cancelled", "Cancelado");
		statusList.put("NotCommitted", "No Comprometido");
		response.put("statusList", statusList);
		
		turnList = new LinkedHashMap<String, String>();
		turnList.put("NotSelected", "Seleciona un turno");
		turnList.put("0", "00:00am");
		turnList.put("1", "00:30am");
		turnList.put("2", "01:00am");
		turnList.put("3", "01:30am");
		turnList.put("4", "02:00am");
		turnList.put("5", "02:30am");
		turnList.put("6", "03:00am");
		turnList.put("7", "03:30am");
		turnList.put("8", "04:00am");
		turnList.put("9", "04:30am");
		turnList.put("10", "05:00am");
		turnList.put("11", "05:30am");
		turnList.put("12", "06:00am");
		turnList.put("13", "06:30am");
		turnList.put("14", "07:00am");
		turnList.put("15", "07:30am");
		turnList.put("16", "08:00am");
		turnList.put("17", "08:30am");
		turnList.put("18", "09:00am");
		turnList.put("19", "09:30am");
		turnList.put("20", "10:00am");
		turnList.put("21", "10:30am");
		turnList.put("22", "11:00am");
		turnList.put("23", "11:30am");
		turnList.put("24", "12:00am");
		turnList.put("25", "12:30pm");
		turnList.put("26", "13:00pm");
		turnList.put("27", "13:30pm");
		turnList.put("28", "14:00pm");
		turnList.put("29", "14:30pm");
		turnList.put("30", "15:00pm");
		turnList.put("31", "15:30pm");
		turnList.put("32", "16:00pm");
		turnList.put("33", "16:30pm");
		turnList.put("34", "17:00pm");
		turnList.put("35", "17:30pm");
		turnList.put("36", "18:00pm");
		turnList.put("37", "18:30pm");
		turnList.put("38", "19:00pm");
		turnList.put("39", "19:30pm");
		turnList.put("40", "20:00pm");
		turnList.put("41", "20:30pm");
		turnList.put("42", "21:00pm");
		turnList.put("43", "21:30pm");
		turnList.put("44", "22:00pm");
		turnList.put("45", "22:30pm");
		turnList.put("46", "23:00pm");
		turnList.put("47", "23:30pm");
		response.put("turnList", turnList);
	}
}

class SimpleTurnComparator implements Comparator<JSPSimpleTurn>{

	@Override
	public int compare(JSPSimpleTurn turn1, JSPSimpleTurn turn2) {
		try {
			int turn1int = SimpleTurn.getTurnByHour(turn1.getTurn());
			int turn2int = SimpleTurn.getTurnByHour(turn2.getTurn());
			if (turn1.getDow().compareTo(turn2.getDow())<0){
				return -1;
			} else {
				if (turn1.getDow().compareTo(turn2.getDow())==0){
					if (turn1int<turn2int){
						return -1;
					} else {
						if (turn1int==turn2int){
							return 0;
						} else return 1;
					}
				} else {
					return 1;
				}
			}
		} catch (TurnException e) {
			throw new RuntimeException("Something is gone really wrong");
		}
	}
}
