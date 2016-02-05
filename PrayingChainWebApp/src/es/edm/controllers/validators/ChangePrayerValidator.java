package es.edm.controllers.validators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import es.edm.domain.JSPPrayer;
import es.edm.domain.Prayer;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.services.MainService;

@Component
public class ChangePrayerValidator implements Validator {
	
	@Autowired 
	EmailValidator emailValidator;
	
	@Autowired
	private MainService main;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//This is an array with 2 elements: (1) is the original data passed; (2) is the values changed in the form by the user
		JSPPrayer prayer2Change = (JSPPrayer)target;
		
		emailValidator = new EmailValidator();
		
		//Validation of name: not empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NameEmpty");
		
		//Validation of Country: not empty
		if (prayer2Change.getOwnCountry().equals("NotSelected")){
			errors.rejectValue("ownCountry", "OwnCountryEmpty");
		}
		
		//Validation of Visibility Profile: not empty
		if (prayer2Change.getHidden().equals("NotSelected")){
			errors.rejectValue("hidden", "HiddenEmpty");
		}
		
		//Validation of Visibility Profile: If Public, then pseudonym should be provided
		if (prayer2Change.getHidden().equals("false") && prayer2Change.getPseudonym().equals("")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pseudonym", "PseudonymEmpty");
		}
		
		//Validation of Visibility Profile: If Hidden, then pseudonym shouldn't be provided
		if (prayer2Change.getHidden().equals("true") && !prayer2Change.getPseudonym().equals("")){
			errors.rejectValue("pseudonym", "PseudonymIsNotEmpty");
		}
		
		//Validation of Email: Should have a valid format and should not be already present into the ddbb
		if (!prayer2Change.getEmail().equals("")){
			if (!emailValidator.validate(prayer2Change.getEmail())){
				errors.rejectValue("email", "EmailNotValid");
			} else {
				try {
					/* If the email was changed, then check if that new email is present into the ddbb
					 * If the email is the same as original one, then there is no need to check it out
					 */
					if (!prayer2Change.getOriginalEmail().equals(prayer2Change.getEmail())){
						//Try to find that email in the ddbb
						@SuppressWarnings("unused")
						Prayer emailFoundPrayer = main.getPrayerByEmail(prayer2Change.getEmail());

						//If this is run, it seems that that email was found, and then an error should be raised
						errors.rejectValue("email", "EmailAlreadyExists");
					}
				} catch (PrayerNotFoundException e) {
				}
			}
		}
		
		//Validation of phone, in case email is not set, and to check if that phone already exists in the ddbb
		if (prayer2Change.getEmail().equals("")){
			//If phone was provided...
			if (!prayer2Change.getPhone().equals("")){
				try {
					//Try to get all prayers by that phone
					@SuppressWarnings("unused")
					List<Prayer> foundPrayersByPhone = main.getPrayersByPhone(prayer2Change.getPhone());
					
					//If this is reached, then an error should be raised, as it was prayers with the same phone into the ddbb
					errors.rejectValue("phone", "PhoneAlreadyExists");
				} catch (PrayerNotFoundException e) {
				} catch (EmptyParameterException e) {
				}
			}
		}
		
		//Validation of name, in case neither email nor phone were provided
		if (prayer2Change.getEmail().equals("") && prayer2Change.getPhone().equals("")){
			boolean foundError = false;
			if (!prayer2Change.getName().equals("")){
				try {
					//Try to find other prayers with the same email
					List<Prayer> foundPrayersByName = main.getPrayersByName(prayer2Change.getName());
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
	}
}
