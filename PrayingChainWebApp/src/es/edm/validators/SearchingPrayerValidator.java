package es.edm.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.model.Prayer;

@Component
public class SearchingPrayerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Prayer prayer = (Prayer)target;
		if (prayer.getEmail().trim().equals("") && prayer.getName().trim().equals("") && 
			prayer.getPhone().trim().equals("") && prayer.getNotes().trim().equals("") &&
			!prayer.isHidden() && !prayer.isOwnCountry()) {
			errors.reject("NoParameterSpecified");
		}
	}

}
