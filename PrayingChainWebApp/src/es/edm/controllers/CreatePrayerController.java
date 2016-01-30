package es.edm.controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.PrayerAlreadyExistsException;
import es.edm.model.JSPPrayer;
import es.edm.model.Prayer;
import es.edm.services.MainService;
import es.edm.validators.CreatingPrayerValidator;

@Controller
@RequestMapping(path="/createPrayer")
public class CreatePrayerController {
	
	//Response Model
	private Map<String, Object> model;
	
	@Autowired
	private CreatingPrayerValidator validator;
	
	@Autowired
	private MainService main;
	
	//Combo Lists
	private Map<String, String> ownCountryList;
	private Map<String, String> hiddenList;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showForm(){
		model = new LinkedHashMap<String, Object>();
		model.put("newPrayer", new JSPPrayer());

		//Load Combos
		loadComboOwnCountry();
		loadComboHiddenList();

		return new ModelAndView("/web/createPrayer.jsp", model);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("newPrayer") JSPPrayer prayerSearched, Errors errors){

		//Backing Object
		model = new LinkedHashMap<String, Object>();
		
		//Load Combos
		loadComboOwnCountry();
		loadComboHiddenList();

		//Inject back the backing object
		model.put("newPrayer", prayerSearched);
		
		//Validate that form entries
		validator.validate(prayerSearched, errors);

		//If there were any errors, return back to the initial form
		if (errors.hasErrors()){
			return new ModelAndView("/web/createPrayer.jsp", model);
		}

		//Let's create a new prayer!
		//Prayer ID is needed, but it would be recreated by the service
		String name = prayerSearched.getName();
		String email = prayerSearched.getEmail();
		String phone = prayerSearched.getPhone();
		boolean ownCountry;
		if (prayerSearched.getOwnCountry().equals("Spain")) {
			ownCountry = true;
		} else {
			ownCountry = false;
		}
		Date date = new Date();
		String notes = prayerSearched.getNotes();
		boolean hidden;
		if (prayerSearched.getHidden().equals("Public")) {
			hidden = false;
		} else {
			hidden = true;
		}
		String pseudonym = prayerSearched.getPseudonym();
		
		Prayer newPrayer = new Prayer(10000, name, email, phone, ownCountry, date, notes, hidden, pseudonym);

		try {
			main.addNewPrayer(newPrayer);
			
			//In order to avoid its own validator restrictions, let's remove no needed filters...
			if (!prayerSearched.getEmail().equals("")){
				prayerSearched.setName("");
				prayerSearched.setPhone("");
			} else {
				if (!prayerSearched.getPhone().equals("")){
					prayerSearched.setName("");
				}
			}
			
			return new ModelAndView("/web/newPrayerCreated.jsp", model);
		} catch (PrayerAlreadyExistsException e) {
			throw new RuntimeException("Something really bad has happen, as it shouldn't be raised a PrayerAlreadyExistsException... but it was");
		}
	}
	
	private void loadComboOwnCountry(){
		ownCountryList = new LinkedHashMap<String, String>();
		ownCountryList.put("NotSelected", "Please, specify Country");
		ownCountryList.put("Spain", "España");
		ownCountryList.put("Other", "Otro País");
		model.put("ownCountryList", ownCountryList);
	}
	
	private void loadComboHiddenList(){
		hiddenList = new LinkedHashMap<String, String>();
		hiddenList.put("NotSelected", "Please, select visibility profile") ; 
		hiddenList.put("Hidden", "Oculto"); 
		hiddenList.put("Public", "Público");
		model.put("hiddenList", hiddenList);
	}
}
