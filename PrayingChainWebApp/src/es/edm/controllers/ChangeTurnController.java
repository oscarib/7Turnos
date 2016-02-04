package es.edm.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.domain.JSPSimpleTurn;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.TurnException;
import es.edm.services.MainService;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;
import es.edm.validators.ChangeTurnValidator;

@Controller
@RequestMapping(path="/changeTurn")
public class ChangeTurnController {

	private Map<String, Object> model;
	
	@Autowired
	private ChangeTurnValidator validator;
	
	@Autowired
	MainService main;
	
	@RequestMapping(method=RequestMethod.GET)
	//If the user calls this page directly, then redirects him to cannotCreateTurnDirectly.jsp
	public ModelAndView showForm(){
		
		//This use Case should not be called directly, so if so, redirects the user to showPrayers
		return new ModelAndView("/web/cannotCallCreateTurnDirectly.jsp", model);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("turn2Change") JSPSimpleTurn turn2Change, Errors errors){
		
		resetForm();
		model.put("turn2Change", turn2Change);
		
		/* If this is the first hit to the page from another page, then it doesn't need to be validated.
		 * Just, present the form to the user 
		 */
		if (!turn2Change.isFirstCall()){
			
			turn2Change.setFirstCall(false);

			validator.validate(turn2Change, errors);

			//Prepare a simpleTurn
			if (errors.hasErrors()){
				return new ModelAndView("/web/ChangeTurn.jsp", model);
			}
			
			int turnUid = Integer.parseInt(turn2Change.getUid());
			int prayerID = Integer.parseInt(turn2Change.getPrayer_id());
			DayOfWeek dow = DayOfWeek.valueOf(turn2Change.getDow());
			int turn2Add = turn2Change.getTurnInt();
			TurnStatus status = TurnStatus.valueOf(turn2Change.getStatus());
			SimpleTurn turn2BeAdded = new SimpleTurn(turnUid, prayerID, dow, turn2Add, status, turn2Change.getNotes(), 1);
			try {
				main.changeTurn(turn2BeAdded);
				return new ModelAndView("/web/TurnModified.jsp");
			} catch (TurnException e) {
				throw new RuntimeException("There were a fatal error while trying to update a turn into the ddbb. " + e.toString());
			}

		} else {
			return new ModelAndView("/web/ChangeTurn.jsp", model);
		}

		//If errors, there's no need to process the form. The user should correct any error first
	}
	
	//This will load combos lists in the form
	private void loadCombos(){
		
		//Combo for dow
		Map<String, String> dayList = new LinkedHashMap<String, String>();
		dayList.put("NotSelected", "Choose a day of week");
		dayList.put("monday", "Lunes");
		dayList.put("tuesday", "Martes");
		dayList.put("wednesday", "Miércoles");
		dayList.put("thursday", "Jueves");
		dayList.put("friday", "Viernes");
		dayList.put("saturday", "Sábado");
		dayList.put("sunday", "Domingo");
		model.put("dayList", dayList);
		
		//Combo for status
		Map<String, String> statusList = new LinkedHashMap<String, String>();
		statusList.put("NotSelected", "Choose a status");
		statusList.put("accepted", "Aceptado");
		statusList.put("cancelled", "Cancelado");
		statusList.put("NotCommitted", "No comprometido");
		model.put("statusList", statusList);
		
		//Combo for turn
		Map<String, String> turnList = new LinkedHashMap<String, String>();
		turnList.put("NotSelected", "Choose a turn");
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
		turnList.put("24", "12:00pm");
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
		model.put("turnList", turnList);
	}
	
	private void resetForm(){
		model = new LinkedHashMap<String, Object>();
		loadCombos();
	}
}
