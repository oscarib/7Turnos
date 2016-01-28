package es.edm.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.edm.exceptions.DayOfWeekException;
import es.edm.exceptions.TurnException;
import es.edm.model.JSPSimpleTurn;
import es.edm.model.SimpleTurn;
import es.edm.services.MainService;
import es.edm.util.DayOfWeek;

@Component
public class NewTurnValidator implements Validator {

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

		//If it is the frist call to 
		if (newTurn.getDow().equals("NotSelected")) errors.rejectValue("dow", "DowEmpty");
		if (newTurn.getStatus().equals("NotSelected")) errors.rejectValue("status", "StatusEmpty");
		if (newTurn.getTurn().equals("NotSelected")) errors.rejectValue("turn", "TurnEmpty");

		//If the prayer already has this turn, then raise an error
		//First, dow and turn should be selected
		if (!newTurn.getDow().equals("NotSelected") && !newTurn.getTurn().equals("NotSelected")){

			//Convert prayerID to int
			int prayerID = Integer.parseInt(newTurn.getPrayer_id());

			//Convert dow to DayOfWeek accepted values
			DayOfWeek dow;
			try {
				dow = SimpleTurn.getDayOfWeek(newTurn.getDow());
			} catch (DayOfWeekException e) {
				throw new RuntimeException("DayOfWeek convertion failed. Form value: " + newTurn.getDow());
			}

			//Convert turn to int
			int turn = Integer.parseInt(newTurn.getTurn());

			//If this turn on this day is already prayed by this prayer, then error
			try {
				if (main.isTurnPrayedByPrayer(prayerID, dow, turn)){
					errors.reject("error.TurnAlreadyPrayedByPrayer");
				}
			} catch (TurnException e) {
				throw new RuntimeException("Something went really wrong with this turn of " + newTurn.getTurn());
			}
		}
	}
	
	private void resetFields(JSPSimpleTurn newTurn){
		if (newTurn.getDow()==null) newTurn.setDow("NotSelected");
		if (newTurn.getStatus()==null) newTurn.setStatus("NotSelected");
		if (newTurn.getTurn()==null) newTurn.setTurn("NotSelected");
	}
}
