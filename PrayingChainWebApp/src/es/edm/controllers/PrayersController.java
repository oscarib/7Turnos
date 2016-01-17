package es.edm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.model.Prayer;
import es.edm.services.MainService;
import es.edm.validators.SearchingPrayerValidator;

@Controller
public class PrayersController {

	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingPrayerValidator searchingPrayervalidator;

	@RequestMapping(path="/showPrayers", method=RequestMethod.GET)
	public ModelAndView searchPrayers() {
		//TODO-1: Write Controller searchPrayersGET()
		return new ModelAndView("/web/showPrayers.jsp", "prayer", new Prayer());
	}
	
	/* SEARCH PRAYERS. PROCCESSING OF THE FORM
	 * Search for prayers into the DDBB, dependending on the search fields.
	 * If two parameters are set, then a mixed list is returned, with an AND method
	 */
	@RequestMapping(path="/showPrayers", method=RequestMethod.POST)
	public ModelAndView searchPrayers(Prayer prayerSearched, Errors errors) {
		
		//Validate that form entries
		searchingPrayervalidator.validate(prayerSearched, errors);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/showPrayers.jsp", "prayer", prayerSearched);
		}
		
		//To hold all the data that should be returned back to the view
		Map<String, Object> response = new HashMap<String, Object>();
		
		//Takes the backing Object form the Form
		response.put("prayer", prayerSearched);

		//NAME
		//Try to get all prayers by Name
		try {
			List<Prayer> prayers = main.getPrayersByName(prayerSearched.getName());
			response.put("prayers", prayers);
		} catch (PrayerNotFoundException e) {
		}
		
		//EMAIL
		/* If EMAIL was provided, take the prayer, and reset the previous lists of prayers, as it cannot be
		 * found no more than a single match for a given email
		 */
		if (prayerSearched.getEmail()!=null){
			try {
				Prayer prayer = main.getPrayerByEmail(prayerSearched.getEmail());
				List<Prayer> prayers = new ArrayList<Prayer>();
				prayers.add(prayer);
				response.put("prayers", prayers);
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//PHONE
		/* if PHONE was provided, ask for such prayers, and mix the result with the 
		 * actual results on previous searches
		 */
		if (prayerSearched.getPhone()!=null && !prayerSearched.getPhone().trim().equals("")){
			try {
				//Take the list of Prayer by Phone
				List<Prayer> newList;
				try {
					newList = main.getPrayersByPhone(prayerSearched.getPhone());
					@SuppressWarnings("unchecked")
					//Take the previous list of prayers already set on the response
					List<Prayer> previousList = (List<Prayer>)response.get("prayers");
					//mix the two lists (and method)
					List<Prayer> mixedListOfPrayers = main.andMixingOfLists(previousList, newList);
					//put the new mixed list on the response
					response.put("prayers", mixedListOfPrayers);
					
				//if the provided phone string is empty
				} catch (EmptyParameterException e) {
				}
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//NOTES MASK
		/* if a NOTES MASK was provided, ask for such prayers, and mix the result with the 
		 * actual results on previous searches
		 */
		if (prayerSearched.getNotes()!=null && !prayerSearched.getNotes().trim().equals("")){
			try {
				//Take the list of Prayer by Phone
				List<Prayer> newList;
				newList = main.getPrayersByNotes(prayerSearched.getNotes());
				@SuppressWarnings("unchecked")
				//Take the previous list of prayers already set on the response
				List<Prayer> previousList = (List<Prayer>)response.get("prayers");
				//mix the two lists (and method)
				List<Prayer> mixedListOfPrayers = main.andMixingOfLists(previousList, newList);
				//put the new mixed list on the response
				response.put("prayers", mixedListOfPrayers);
				//if the provided phone string is empty
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//ISHIDDEN
		try {
			List<Prayer> newList;
			if (prayerSearched.isHidden()) {
				newList = main.getHiddenPrayers();
			} else {
				newList = main.getPublicPrayers();
			}
			@SuppressWarnings("unchecked")
			List<Prayer> previousList = (List<Prayer>)response.get("prayers");
			//mix the two lists (and method)
			List<Prayer> mixedListOfPrayers = main.andMixingOfLists(previousList, newList);
			//put the newe mixed list on the response
			response.put("prayers", mixedListOfPrayers);
		} catch (PrayerNotFoundException e) {
		}
		
		//OWNCOUNTRY
		try {
			List<Prayer> newList;
			if (prayerSearched.isOwnCountry()) {
				newList = main.getLocalPrayers();
			} else {
				newList = main.getForeignPrayers();
			}
				@SuppressWarnings("unchecked")
				List<Prayer> previousList = (List<Prayer>)response.get("prayers");
				//mix the two lists (and method)
				List<Prayer> mixedListOfPrayers = main.andMixingOfLists(previousList, newList);
				//put the newe mixed list on the response
				response.put("prayers", mixedListOfPrayers);
		} catch (PrayerNotFoundException e) {
		}
		
		//Provide number of rows returned
		@SuppressWarnings("unchecked")
		List<Prayer> finalList = (List<Prayer>)response.get("prayers");
		response.put("size", finalList.size());

		//TODO-1-End: Finish Controller searchPrayersPOST()
		return new ModelAndView("/web/showPrayers.jsp", "response", response);
	}

	@RequestMapping(path="/deletePrayer")
	public ModelAndView deletePrayer(String prayerId){
		//TODO: Write Controller deletePrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/changePrayer")
	public ModelAndView changePrayer(String prayerID){
		//TODO: Write Controller changePrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping(path="/addPrayer")
	public ModelAndView addPrayer(){
		//TODO: Write Controller addPrayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping(path="/addPrayer2Turn")
	public ModelAndView addPrayer2Turn(){
		//TODO: Write Controller addPrayer2Turn()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
}
