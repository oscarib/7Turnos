package es.edm.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.services.MainService;

@Controller
public class getStatistics {

	@Autowired
	private MainService main;
	
	HashMap<String,String> statistics;
	
	@ResponseBody
	@RequestMapping(value = "/getStatistics.do", method = RequestMethod.POST)
	public Map<String,String> getallStatistics() {
		statistics = new HashMap<String, String>();
		statistics.put("TotalTurns", Integer.toString(main.getTotalTurns()));
		statistics.put("TurnsCovered", Integer.toString(main.getUsedTurns()));
		int availableTurns = main.getTotalTurns()-main.getUsedTurns();
		statistics.put("AvailableTurns", Integer.toString(availableTurns));
		statistics.put("EmptyTurns", Integer.toString(main.getEmptyTurns()));
		int committedPrayers = main.getNumberOfCommittedPrayers();
		int nonCommittedPrayers = main.getNumberOfNonCommittedPrayers();
		statistics.put("CommittedPrayers", Integer.toString(committedPrayers));
		statistics.put("NonCommittedPrayers", Integer.toString(nonCommittedPrayers));
		int totalPrayers = committedPrayers+nonCommittedPrayers;
		statistics.put("TotalPrayers", Integer.toString(totalPrayers));
		statistics.put("TurnsUsedPercentage", main.getTurnsUsedPercentage());
		statistics.put("FreeTurnsPercentage", main.getFreeTurnsPercentage());
		statistics.put("EmptyTurnsPercentage", main.getEmptyTurnsPercentage());
		statistics.put("CommittedRedundancy", main.getCommittedRedundancy());
		statistics.put("TotalRedundancy",  main.getTotalRedundancy());
		statistics.put("HiddenPrayers", Integer.toString(main.getHiddenPrayers().size()));
		statistics.put("PublicPrayers", Integer.toString(main.getPublicPrayers().size()));
		statistics.put("ForeignPrayers", Integer.toString(main.getForeignPrayers().size()));
		statistics.put("LocalPrayers", Integer.toString(main.getLocalPrayers().size()));
		statistics.put("OrphanTurns", Integer.toString(main.getOrphanTurns().size()));
		statistics.put("OrphanPrayers", Integer.toString(main.getOrphanPrayers().size()));
		return statistics;
	}
}