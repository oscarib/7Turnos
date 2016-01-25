package es.edm.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.model.JSPPrayer;
import es.edm.model.Prayer;

@Component
public class SearchingPrayerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JSPPrayer prayer = (JSPPrayer)target;
		if (prayer.getName().equals("") && prayer.getEmail().equals("") && prayer.getHidden().equals("NotSelected") && prayer.getNotes().equals("") && 
			prayer.getOwnCountry().equals("NotSelected") && prayer.getPhone().equals("")){
			errors.reject("NoParameterSpecified");
		}
	}

}
