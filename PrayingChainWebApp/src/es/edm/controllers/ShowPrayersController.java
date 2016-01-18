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


/* Controls the user request for showPrayer.html
 * - Presents a searching form and perform searches into the ddbb
 * - Adds links for creating and deleting prayers for the returned prayers
 * - Also, adds links for browsing turns of a particular prayer
 * - TODO: If there are prayers with no turns on the ddbb, it also present them to the user
 * 		   in order to let know about it. It doesn't matter if such prayer is within the
 * 		   defined filter or not: it would be shown	
 */
@Controller
public class showPrayersController {

	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingPrayerValidator searchingPrayervalidator;
	
	//For saving data to show on the View (JSP)
	private Map<String, Object> response;
	
	public showPrayersController(){
		//To hold all the data that should be returned back to the view
		response = new HashMap<String, Object>();
	}

//CONTROLLER METHODS // CONTROLLER METHODS // CONTROLLER METHODS
	
	//Presents the form
	@RequestMapping(path="/showPrayers", method=RequestMethod.GET)
	public ModelAndView searchPrayers() {
		return new ModelAndView("/web/showPrayers.jsp", "prayer", new Prayer());
	}
	
	//Processes the form
	@RequestMapping(path="/showPrayers", method=RequestMethod.POST)
	public ModelAndView searchPrayers(Prayer prayerSearched, Errors errors) {
		
	//Constructs the prayer list
		
		//Validate that form entries
		searchingPrayervalidator.validate(prayerSearched, errors);
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/showPrayers.jsp", "prayer", prayerSearched);
		}
		
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
		/* If EMAIL was provided, take the prayer, and reset the previous lists of prayers, 
		 * as it cannot be found no more than a single match for a given email
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
				mixOfLists(main.getPrayersByPhone(prayerSearched.getPhone()));
			} catch (PrayerNotFoundException e) {
			} catch (EmptyParameterException e) {
			}
		}
		
		//NOTES MASK
		/* if a NOTES MASK was provided, ask for such prayers, and mix the result with the 
		 * actual results on previous searches
		 */
		if (prayerSearched.getNotes()!=null && !prayerSearched.getNotes().trim().equals("")){
			try {
				mixOfLists(main.getPrayersByNotes(prayerSearched.getNotes()));
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
			mixOfLists(newList);
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
			mixOfLists(newList);
		} catch (PrayerNotFoundException e) {
		}
		
		//Provide number of rows returned
		@SuppressWarnings("unchecked")
		List<Prayer> finalList = (List<Prayer>)response.get("prayers");
		response.put("prayersSize", finalList.size());
		
	//Construct the errors lists
		List<Prayer> orphanPrayers = main.getOrphanPrayers();
		response.put("orphanPrayers", orphanPrayers);
		response.put("errorsSize", orphanPrayers.size());

		return new ModelAndView("/web/showPrayers.jsp", "response", response);
	}
	

//PRIVATE METHODS // PRIVATE METHODS // PRIVATE METHODS

	private void mixOfLists(List<Prayer> newList){
		//Take the previous list of prayers already set on the response
		@SuppressWarnings("unchecked")
		List<Prayer> previousList = (List<Prayer>)response.get("prayers");
		//mix the two lists (and method)
		List<Prayer> mixedListOfPrayers;
		try {
			mixedListOfPrayers = main.andMixingOfLists(previousList, newList);
			//put the new mixed list on the response
			response.put("prayers", mixedListOfPrayers);
		} catch (EmptyParameterException e) {
		}
	}
}