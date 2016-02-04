package es.edm.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.domain.JSPPrayer;
import es.edm.domain.Prayer;

@Component
public class SearchingPrayerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		JSPPrayer prayer = (JSPPrayer)target;

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
		
		//Error if no field is setup
		if (prayer.getUid().equals("") && prayer.getName().equals("") && prayer.getEmail().equals("") && prayer.getHidden().equals("NotSelected") && 
			prayer.getNotes().equals("") && prayer.getOwnCountry().equals("NotSelected") && prayer.getPhone().equals("")){
			errors.reject("NoParameterSpecified");
		}
		
		//If UID, then no other field allowed
		if (!prayer.getUid().equals("")){
			if (!prayer.getName().equals("") || !prayer.getEmail().equals("") || !prayer.getHidden().equals("NotSelected") || 
				!prayer.getNotes().equals("") || !prayer.getOwnCountry().equals("NotSelected") || !prayer.getPhone().equals("")){
				errors.reject("UidSpecified");
			}
		}
		
		//If Email, then no other field allowed
		if (!prayer.getEmail().equals("") && prayer.getUid().equals("")){
			if (!prayer.getName().equals("") || !prayer.getHidden().equals("NotSelected") || 
				!prayer.getNotes().equals("") || !prayer.getOwnCountry().equals("NotSelected") || !prayer.getPhone().equals("")){
				errors.reject("EmailSpecified");
			}
		}

		//Integer error
		try{
			if (!prayer.getUid().equals("")){
				@SuppressWarnings("unused")
				int uid = Integer.parseInt(prayer.getUid());
			}
		} catch (NumberFormatException e){
			errors.rejectValue("uid", "NotANumber");
		}

	}

}
