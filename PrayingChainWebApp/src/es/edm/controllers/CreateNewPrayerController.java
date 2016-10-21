package es.edm.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.domain.middle.NewPrayerAndTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.services.Configuration;
import es.edm.services.MainService;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;

@Controller
public class CreateNewPrayerController {

	@Autowired
	MainService main;
	
	@Autowired
	Configuration conf;
	
	@Autowired
	ResourceBundleMessageSource properties;

	/* Comprueba si existen más oradores con el mismo email y teléfono
	 * En caso contrario, crea el orador en ddbb*/
	@ResponseBody
	@RequestMapping(path="/createNewPrayer.do", method=RequestMethod.POST)
	@Transactional
	public int createNewPrayer(@RequestBody NewPrayerAndTurn newPrayerAndTurn) throws IOException, DDBBException{

		int error = 0;

		//Comprobamos si ese email ya existe
		try{
			@SuppressWarnings("unused")
			Prayer foundPrayer = main.getPrayerByEmail(newPrayerAndTurn.getEmail());
			error = 1;
			
		} catch (PrayerNotFoundException ex){
			
			//Comprobamos si ese teléfono ya existe
			try{
				@SuppressWarnings("unused")
				List<Prayer> foundPrayer = main.getPrayersByPhone(newPrayerAndTurn.getTelefono());
				error = 2;
				
			} catch (PrayerNotFoundException ex2){
				
				//Creación del orador
				Prayer newPrayer = new Prayer();
				newPrayer.setName(newPrayerAndTurn.getNombre());
				newPrayer.setEmail(newPrayerAndTurn.getEmail());
				newPrayer.setPhone(newPrayerAndTurn.getTelefono());
				String ownCountryString = properties.getMessage("prayer.ownCountry", null, Locale.getDefault());
				boolean ownCountry = (ownCountryString.equals(newPrayerAndTurn.getPais()) ? true : false);
				newPrayer.setOwnCountry(ownCountry);
				newPrayer.setOptinDate(new Date());
				newPrayer.setNotes(newPrayerAndTurn.getNotas());
				newPrayer.setPseudonym(newPrayerAndTurn.getSeudonimo());
				String hiddenString = properties.getMessage("turn.hidden", null, Locale.getDefault());
				boolean hidden = (hiddenString.equals(newPrayerAndTurn.getVisibilidad()) ? true : false);
				newPrayer.setHidden(hidden);
				
				main.addNewPrayer(newPrayer);
				
				//Obtención del ID del orador
				int prayerID = 0;
				if ("".equals(newPrayerAndTurn.getEmail()) || newPrayerAndTurn.getEmail() == null){
					if ("".equals(newPrayerAndTurn.getTelefono()) || newPrayerAndTurn.getTelefono()==null){
						throw new RuntimeException("No pudo encontrarse el orador ni por email ni por teléfono");
					} else {
						prayerID = main.getPrayerByPhone(newPrayerAndTurn.getTelefono()).getUid();
					}
				} else {
					prayerID = main.getPrayerByEmail(newPrayerAndTurn.getEmail()).getUid();
				}
				
				//Creación del turno asociado
				SimpleTurn newTurn = new SimpleTurn();
				DayOfWeek dow = SimpleTurn.getDayOfWeek(newPrayerAndTurn.getDia());
				newTurn.setDow(dow);
				newTurn.setNotes(newPrayerAndTurn.getNotas());
				newTurn.setPax(1);
				newTurn.setPrayer_id(prayerID);
				newTurn.setStatus(TurnStatus.accepted);
				newTurn.setTurn(SimpleTurn.getTurnByHour(newPrayerAndTurn.getTurno()));
				main.addTurn(prayerID, newTurn);
			}
		}
		
		return error;
	}
}