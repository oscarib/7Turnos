package es.edm.controllers.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateValidator {

	public boolean isThisDateValid(String dateToValidate, String dateFromat){
		
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			@SuppressWarnings("unused")
			Date date = sdf.parse(dateToValidate);
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}