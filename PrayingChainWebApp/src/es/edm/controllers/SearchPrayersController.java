package es.edm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.controllers.validators.SearchingPrayerValidator;
import es.edm.domain.JSPPrayer;
import es.edm.domain.Prayer;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.services.MainService;

@Controller
@RequestMapping(value = "/showPrayers")
public class SearchPrayersController {
	@Autowired
	private MainService main;
	
	@Autowired
	private SearchingPrayerValidator searchingPrayervalidator;
	
	//For saving data to show on the View (JSP)
	private Map<String, Object> response;
	
	public SearchPrayersController(){
		resetForm();
	}

//CONTROLLER METHODS // CONTROLLER METHODS // CONTROLLER METHODS
	
	//Presents the form
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView searchPrayers() {
		resetForm();
		return new ModelAndView("/web/showPrayers.jsp", response);
	}
	
	//Processes the form
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView searchPrayers(@ModelAttribute("prayer") JSPPrayer prayerSearched, Errors errors) {
		
	//Constructs the prayer list
		
		//Validate that form entries
		searchingPrayervalidator.validate(prayerSearched, errors);
		
		resetForm();
		
		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			resetForm();
			return new ModelAndView("/web/showPrayers.jsp", response);
		}
		
		//UID
		if (!prayerSearched.getUid().equals("")){
			try {
				Prayer prayer = main.getPrayerByID(Integer.parseInt(prayerSearched.getUid()));
				List<Prayer> prayers = new ArrayList<Prayer>();
				prayers.add(prayer);
				response.put("prayers", prayers);

				processForm();
				return new ModelAndView("/web/showPrayers.jsp", response);
				
			} catch (PrayerNotFoundException e) {
				processForm();
				return new ModelAndView("/web/showPrayers.jsp", response);
			} 
		}
		
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
		if (!prayerSearched.getEmail().equals("")){
			try {
				resetForm();
				Prayer prayer = main.getPrayerByEmail(prayerSearched.getEmail());
				List<Prayer> prayers = new ArrayList<Prayer>();
				prayers.add(prayer);
				response.put("prayers", prayers);
				processForm();
				return new ModelAndView("/web/showPrayers.jsp", response);
			} catch (PrayerNotFoundException e) {
				processForm();
				return new ModelAndView("/web/showPrayers.jsp", response);
			}
		}
		
		//PHONE
		/* if PHONE was provided, ask for such prayers, and mix the result with the 
		 * actual results on previous searches
		 */
		if (!prayerSearched.getPhone().trim().equals("")){
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
		if (!prayerSearched.getNotes().trim().equals("")){
			try {
				mixOfLists(main.getPrayersByNotes(prayerSearched.getNotes()));
			} catch (PrayerNotFoundException e) {
			}
		}
		
		//ISHIDDEN
		try {
			List<Prayer> newList;
			if (prayerSearched.getHidden().equals("Hidden")) {
				newList = main.getHiddenPrayers();
				mixOfLists(newList);
			} else {
				if (prayerSearched.getHidden().equals("Public")) {
					newList = main.getPublicPrayers();
					mixOfLists(newList);
				}
			}
		} catch (PrayerNotFoundException e) {
		}
		
		//OWNCOUNTRY
		try {
			List<Prayer> newList;
			if (prayerSearched.getOwnCountry().equals("Spain")) {
				newList = main.getLocalPrayers();
				mixOfLists(newList);
			} else {
				if (prayerSearched.getOwnCountry().equals("Other")) {
					newList = main.getForeignPrayers();
					mixOfLists(newList);
				}
			}
		} catch (PrayerNotFoundException e) {
		}
		
		processForm();
		return new ModelAndView("/web/showPrayers.jsp", response);
	}
	

//PRIVATE METHODS // PRIVATE METHODS // PRIVATE METHODS

	private void processForm(){
		@SuppressWarnings("unchecked")
		List<Prayer> finalList = (List<Prayer>)response.get("prayers");
		if (finalList!=null){
			response.put("prayersSize", finalList.size());
		}

		//Construct the errors lists
		List<Prayer> orphanPrayers = main.getOrphanPrayers();
		response.put("orphanPrayers", orphanPrayers);
		response.put("errorsSize", orphanPrayers.size());
	}
	
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
	
	private void resetForm(){
		//To hold all the data that should be returned back to the view
		response = new HashMap<String, Object>();
		
		//Inject the backing Object
		response.put("prayer", new JSPPrayer());
		
		//Inject hidden values
		Map<String, String> hiddenList = new LinkedHashMap<String, String>();
		hiddenList.put("NotSelected", "Filtrar por Visibilidad");
		hiddenList.put("Hidden", "Oculto");
		hiddenList.put("Public", "Publico");
        response.put("hiddenList", hiddenList);
        
		//Inject OwnCountry values
		Map<String, String> ownCountryList = new LinkedHashMap<String, String>();
		ownCountryList.put("NotSelected", "Filtrar por Pais");
		ownCountryList.put("Spain", "España");
		ownCountryList.put("Other", "Otro País");
        response.put("ownCountryList", ownCountryList);
	}
}