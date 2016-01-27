package es.edm.validators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.model.JSPPrayer;
import es.edm.model.Prayer;
import es.edm.services.MainService;

@Component
public class CreatingPrayerValidator implements Validator {
	
	@Autowired 
	EmailValidator emailValidator;
	
	@Autowired
	private DateValidator dateValidator;
	
	@Autowired
	private MainService main;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		JSPPrayer prayer = (JSPPrayer)target;
		emailValidator = new EmailValidator();
		dateValidator = new DateValidator();

		//Initialitiation of fields when comming from another page
		if (prayer.getEmail()==null) prayer.setEmail("");
		if (prayer.getHidden()==null) prayer.setHidden("NotSelected");
		if (prayer.getName()==null) prayer.setName("");
		if (prayer.getNotes()==null) prayer.setNotes("");
		if (prayer.getOptinDate()==null) prayer.setOptinDate("");
		if (prayer.getOwnCountry()==null) prayer.setOwnCountry("NotSelected");
		if (prayer.getPhone()==null) prayer.setPhone("");
		if (prayer.getPseudonym()==null) prayer.setPseudonym("");
		if (prayer.getUid()==null) prayer.setUid("");
		
		//Validation of name: not empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NameEmpty");
		
		//Validation of OptinDate: not empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "optinDate", "OptinDateEmpty");
		
		//Validation of Country: not empty
		if (prayer.getOwnCountry().equals("NotSelected")){
			errors.rejectValue("ownCountry", "OwnCountryEmpty");
		}
		
		//Validation of Visibility Profile: not empty
		if (prayer.getHidden().equals("NotSelected")){
			errors.rejectValue("hidden", "HiddenEmpty");
		}
		
		//Validation of Visibility Profile: If Public, then pseudonym should be provided
		if (prayer.getHidden().equals("Public") && prayer.getPseudonym().equals("")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pseudonym", "PseudonymEmpty");
		}
		
		//Validation of Visibility Profile: If Hidden, then pseudonym shouldn't be provided
		if (prayer.getHidden().equals("Hidden") && !prayer.getPseudonym().equals("")){
			errors.rejectValue("pseudonym", "PseudonymIsNotEmpty");
		}
		
		//Validation of Email: Should have a valid format and should not be already present into the ddbb
		if (!prayer.getEmail().equals("")){
			if (!emailValidator.validate(prayer.getEmail())){
				errors.rejectValue("email", "EmailNotValid");
			} else {
				try {
					//Try to find that email in the ddbb
					@SuppressWarnings("unused")
					Prayer emailFoundPrayer = main.getPrayerByEmail(prayer.getEmail());
					
					//If this is run, it seems that that email was found, and then an error should be raised
					errors.rejectValue("email", "EmailAlreadyExists");
				} catch (PrayerNotFoundException e) {
				}
			}
		}
		
		//Validation of phone, in case email is not set, and to check if that phone already exists in the ddbb
		if (prayer.getEmail().equals("")){
			//If phone was provided...
			if (!prayer.getPhone().equals("")){
				try {
					//Try to get all prayers by that phone
					@SuppressWarnings("unused")
					List<Prayer> foundPrayersByPhone = main.getPrayersByPhone(prayer.getPhone());
					
					//If this is reached, then an error should be raised, as it was prayers with the same phone into the ddbb
					errors.rejectValue("phone", "PhoneAlreadyExists");
				} catch (PrayerNotFoundException e) {
				} catch (EmptyParameterException e) {
				}
			}
		}
		
		//Validation of name, in case neither email nor phone were provided
		if (prayer.getEmail().equals("") && prayer.getPhone().equals("")){
			boolean foundError = false;
			if (!prayer.getName().equals("")){
				try {
					//Try to find other prayers with the same email
					@SuppressWarnings("unused")
					List<Prayer> foundPrayersByName = main.getPrayersByName(prayer.getName());
					for (Prayer nextPrayer: foundPrayersByName){
						if (nextPrayer.getEmail()==null || nextPrayer.getEmail().equals("")){
							if (nextPrayer.getPhone()==null || nextPrayer.getPhone().equals("")){
								foundError = true;
							}
						}
					}
					
					//If this is reached, then an error should be raised
					if (foundError) errors.rejectValue("name", "NameAlreadyExists");
				} catch (PrayerNotFoundException e) {
				}
			}
		}
		
		//Validation of Optin Date: Should have a valid format
		if (!prayer.getOptinDate().equals("")){
			if (!dateValidator.isThisDateValid(prayer.getOptinDate(), "dd/MM/yyyy")){
				errors.rejectValue("optinDate", "DateNotValid");
			}
		}
	}
}
