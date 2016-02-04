package es.edm.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.domain.JSPSimpleTurn;
import es.edm.domain.Prayer;

@Component
public class SearchingTurnValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Prayer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JSPSimpleTurn turn = (JSPSimpleTurn)target;
		
		if (turn.getDow()==null) turn.setDow("NotSelected");
		if (turn.getNotes()==null) turn.setNotes("");
		if (turn.getPax()==null) turn.setPax("");
		if (turn.prayer_id==null) turn.setPrayer_id("");
		if (turn.getStatus()==null) turn.setStatus("NotSelected");
		if (turn.getTurn()==null) turn.setTurn("NotSelected");
		if (turn.getUid()==null) turn.setUid("");

		//No data specified
		if (turn.getDow().equals("NotSelected") && turn.getStatus().equals("NotSelected") && turn.getNotes().equals("") &&
			turn.getPrayer_id().equals("") && turn.getUid().equals("") && turn.getTurn().equals("NotSelected")){
			errors.reject("NoParameterSpecified");
		}
		
		//Integer error
		try{
			if (!turn.getPrayer_id().equals("")){
				@SuppressWarnings("unused")
				int prayerId = Integer.parseInt(turn.getPrayer_id());
			}
		} catch (NumberFormatException e){
			errors.rejectValue("prayer_id", "NotANumber");
		}
		try{
			if (!turn.getUid().equals("")){
				@SuppressWarnings("unused")
				int uid = Integer.parseInt(turn.getUid());
			}
		} catch (NumberFormatException e){
			errors.rejectValue("uid", "NotANumber");
		}
		
		//Both UID & prayer UID are not allowed
		if (!turn.getUid().equals("") && !turn.getPrayer_id().equals("")){
			errors.reject("BothUids");
		}
		
		//If UID are specified, then no other filter is allowed
		if (!turn.getUid().equals("") || !turn.getPrayer_id().equals("")){
			if (!turn.getDow().equals("NotSelected") || !turn.getStatus().equals("NotSelected") || !turn.getNotes().equals("")){
				errors.reject("UidSpecified");
			}
		}
	}

}
