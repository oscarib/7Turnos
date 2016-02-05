package es.edm.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.domain.JSPSimpleTurn;
import es.edm.services.MainService;

@Component
public class ChangeTurnValidator implements Validator {

	@Autowired
	MainService main;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		JSPSimpleTurn newTurn = (JSPSimpleTurn)target;
		
		resetFields(newTurn);

		//If it is the first call to 
		if (newTurn.getDow().equals("NotSelected")) errors.rejectValue("dow", "DowEmpty");
		if (newTurn.getStatus().equals("NotSelected")) errors.rejectValue("status", "StatusEmpty");
		if (newTurn.getTurn().equals("NotSelected")) errors.rejectValue("turn", "TurnEmpty");
	}
	
	private void resetFields(JSPSimpleTurn newTurn){
		if (newTurn.getDow()==null) newTurn.setDow("NotSelected");
		if (newTurn.getStatus()==null) newTurn.setStatus("NotSelected");
		if (newTurn.getTurn()==null) newTurn.setTurn("NotSelected");
	}
}
