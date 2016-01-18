package es.edm.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.model.Prayer;
import es.edm.model.SimpleTurn;

@Component
public class SearchingTurnValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}

}
