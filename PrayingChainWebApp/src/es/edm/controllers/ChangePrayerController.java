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

import es.edm.exceptions.PrayerException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.model.JSPPrayer;
import es.edm.model.Prayer;
import es.edm.services.MainService;
import es.edm.validators.ChangePrayerValidator;

@Controller
@RequestMapping(path="/changePrayer")
public class ChangePrayerController {
	
	@Autowired
	private ChangePrayerValidator validator;
	
	@Autowired
	MainService main;
	
	private Map<String, Object> model;
	private Map<String, String> ownCountryList;
	private Map<String, String> hiddenList;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView warnUser(){

		//This use Case should not be called directly, so if so, redirects the user to showPrayers
		return new ModelAndView("/web/cannotCallCreateTurnDirectly.jsp", model);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("prayer2Change") JSPPrayer prayer2Change, Errors errors){
		
		resetForm();
		
		model.put("prayer2Change", prayer2Change);
		/* If this is the first hit to the page from another page, then it doesn't need to be validated.
		 * Just, present the form to the user 
		 */
		if (!prayer2Change.isFirstCall()){
			
			validator.validate(prayer2Change, errors);

			//Prepare a simpleTurn
			if (errors.hasErrors()){
				return new ModelAndView("/web/changePrayer.jsp", model);
			}
			
			int uid = Integer.parseInt(prayer2Change.getUid());
			String name = prayer2Change.getName();
			String email = prayer2Change.getEmail();
			String phone = prayer2Change.getPhone();
			boolean ownCountry = prayer2Change.getOwnCountry().equals("true") ? true : false;
			Date optinDate = new Date();
			String notes = prayer2Change.getNotes();
			boolean hidden = prayer2Change.getHidden().equals("true") ? true : false;
			String pseudonym = prayer2Change.getPseudonym();
			Prayer prayer = new Prayer(uid, name, email, phone, ownCountry, optinDate, notes, hidden, pseudonym);
			try {
				main.changePrayer(prayer);
			} catch (PrayerNotFoundException e) {
				throw new RuntimeException("Alert!!!! this prayer was not found into the ddbb. Prayer ID was " + uid);
			} catch (PrayerException e) {
				throw new RuntimeException("Alert!!!!! There was more than 1 prayer updated!!!! Please, take care of it");
			}
			model.put("prayer2Change", prayer2Change);
			return new ModelAndView("/web/PrayerModified.jsp", model);
			
		} else {
			prayer2Change.setOriginalEmail(prayer2Change.getEmail());
			model.put("prayer2Change", prayer2Change);
			return new ModelAndView("/web/changePrayer.jsp", model);
		}
	}
	
	private void resetForm(){
		model = new LinkedHashMap<String, Object>();
		loadCombos();
	}
	
	private void loadCombos(){
		ownCountryList = new LinkedHashMap<String, String>();
		ownCountryList.put("NotSelected", "Please, specify Country");
		ownCountryList.put("true", "España");
		ownCountryList.put("false", "Otro País");
		model.put("ownCountryList", ownCountryList);

		hiddenList = new LinkedHashMap<String, String>();
		hiddenList.put("NotSelected", "Please, select visibility profile") ; 
		hiddenList.put("true", "Oculto"); 
		hiddenList.put("false", "Público");
		model.put("hiddenList", hiddenList);

	}
}
