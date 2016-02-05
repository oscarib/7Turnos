package es.edm.controllers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserAccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userAccount", "UserEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "PwdEmpty");
	}

}
