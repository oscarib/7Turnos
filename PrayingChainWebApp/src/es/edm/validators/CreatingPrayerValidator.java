package es.edm.validators;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import es.edm.model.JSPPrayer;
import es.edm.model.Prayer;

@Component
public class CreatingPrayerValidator implements Validator {
	
	@Autowired 
	EmailValidator emailValidator;
	
	@Autowired
	private DateValidator dateValidator;
	
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
		
		//Validation of email: not empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EmailEmpty");
		
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
		
		//Validation of Email: Should have a valid format
		if (!prayer.getEmail().equals("") && !emailValidator.validate(prayer.getEmail())){
			errors.rejectValue("email", "EmailNotValid");
		}
		
		//Validation of Optin Date: Should have a valid format
		if (!prayer.getOptinDate().equals("")){
			if (!dateValidator.isThisDateValid(prayer.getOptinDate(), "dd/MM/yyyy")){
				errors.rejectValue("optinDate", "DateNotValid");
			}
		}
	}
}
