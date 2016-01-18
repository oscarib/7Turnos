package es.edm.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.model.SimpleTurn;
import es.edm.services.MainService;
import es.edm.validators.SearchingTurnValidator;

@Controller
public class ShowTurnsController {

	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingTurnValidator searchingTurnvalidator;
	
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
		return new ModelAndView("/web/showTurns.jsp", "SimpleTurn", new SimpleTurn());
	}
	
	//Processes the form
	@RequestMapping(path="/showTurns", method=RequestMethod.POST)
	public ModelAndView searchTurns(SimpleTurn turnSearched, Errors errors) {
		//Validate that form entries
		searchingTurnvalidator.validate(turnSearched, errors);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/showTurns.jsp", "SimpleTurn", turnSearched);
		}
		
		//Gets the backing Object form the Form
		response.put("prayer", turnSearched);

		return new ModelAndView("/web/showTurns.jsp", "SimpleTurn", turnSearched);
	}
}
