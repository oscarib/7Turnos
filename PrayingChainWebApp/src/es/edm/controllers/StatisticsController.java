package es.edm.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.services.MainService;

@Controller
public class StatisticsController {
	
	@Autowired
	MainService main;
	
	//For saving data to show on the View (JSP)
	private Map<String, Object> response;

	public StatisticsController(){
		//To hold all the data that should be returned back to the view
		response = new HashMap<String, Object>();
	}

	@RequestMapping(path="/showStatistics", method=RequestMethod.GET)
	public ModelAndView showStatistics(){
		response.put("TotalTurns", main.getTotalTurns());
		response.put("TurnsCovered", main.getUsedTurns());
		int availableTurns = main.getTotalTurns()-main.getUsedTurns();
		response.put("AvailableTurns", availableTurns);
		response.put("EmptyTurns", main.getEmptyTurns());
		int committedPrayers = main.getNumberOfCommittedPrayers();
		int nonCommittedPrayers = main.getNumberOfNonCommittedPrayers();
		response.put("CommittedPrayers", committedPrayers);
		response.put("NonCommittedPrayers", nonCommittedPrayers);
		response.put("TotalPrayers", (committedPrayers+nonCommittedPrayers));
		response.put("TurnsUsedPercentage", main.getTurnsUsedPercentage());
		response.put("FreeTurnsPercentage", main.getFreeTurnsPercentage());
		response.put("EmptyTurnsPercentage", main.getEmptyTurnsPercentage());
		response.put("CommittedRedundancy", main.getCommittedRedundancy());
		response.put("TotalRedundancy",  main.getTotalRedundancy());
		//TODO: Include Whatsapp warnings
		return new ModelAndView("/web/ShowStatistics.jsp", "response", response);
	}

	@RequestMapping(path="/showStatistics", method=RequestMethod.POST)
	public void showStatistics(Errors errors){
		
	}
}
