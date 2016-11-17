package es.edm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.SimpleTurn;
import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.NewPrayerAndTurn;
import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.IOtherServices;
import es.edm.services.IPrayerService;
import es.edm.services.ITurnService;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;

@Controller
public class PrayerController {

	@Autowired
	private IPrayerService prayerService;
	
	@Autowired
	private ITurnService turnService;
	
	@Autowired
	private IOtherServices otherServices;

	@Autowired
	Configuration conf;
	
	@Autowired
	ResourceBundleMessageSource properties;
	
	@ResponseBody
	@RequestMapping(value = "/getAllPrayers.do", method = RequestMethod.POST)
	public List<PrayerEntity> getAllPrayerList() {
		List<PrayerEntity> prayers2Return = prayerService.getPrayers();
		return prayers2Return;
	}
	
	/* Comprueba si existen más oradores con el mismo email y teléfono
	 * En caso contrario, crea el orador en ddbb*/
	@ResponseBody
	@RequestMapping(path="/createNewPrayer.do", method=RequestMethod.POST)
	public int createNewPrayer(@RequestBody NewPrayerAndTurn newPrayerAndTurn) throws IOException, DDBBException{

		int error = 0;
		PrayerEntity interPrayer = new PrayerEntity();
		PrayerEntity foundPrayer = new PrayerEntity();
		interPrayer.setEmail(newPrayerAndTurn.getEmail());
		interPrayer.setPhone(newPrayerAndTurn.getTelefono());

		//Comprobamos si ese email ya existe
		foundPrayer = prayerService.getPrayerByEmail(interPrayer);
		if (foundPrayer==null){

			//Comprobamos si existe ese teléfono
			List<PrayerEntity> foundPrayers = new ArrayList<PrayerEntity>();
			foundPrayers = prayerService.getPrayersByPhone(interPrayer);
			if (foundPrayers.size()==0 || newPrayerAndTurn.getEmail()!=null){

				//Creación del orador
				PrayerEntity newPrayer = new PrayerEntity();
				newPrayer.setName(newPrayerAndTurn.getNombre());
				newPrayer.setEmail(newPrayerAndTurn.getEmail());
				newPrayer.setPhone(newPrayerAndTurn.getTelefono());
				newPrayer.setChain(otherServices.getLoggedUser().getChain());
				String ownCountryString = properties.getMessage("prayer.ownCountry", null, Locale.getDefault());
				boolean ownCountry = (ownCountryString.equals(newPrayerAndTurn.getPais()) ? true : false);
				newPrayer.setOwnCountry(ownCountry);
				newPrayer.setOptinDate(new Date());
				newPrayer.setNotes(newPrayerAndTurn.getNotas());
				newPrayer.setPseudonym(newPrayerAndTurn.getSeudonimo());
				String hiddenString = properties.getMessage("turn.hidden", null, Locale.getDefault());
				boolean hidden = (hiddenString.equals(newPrayerAndTurn.getVisibilidad()) ? true : false);
				newPrayer.setHidden(hidden);

				//Creación del turno asociado
				TurnEntity newTurn = new TurnEntity();
				DayOfWeek dow = SimpleTurn.getDayOfWeek(newPrayerAndTurn.getDia());
				newTurn.setDow(dow);
				newTurn.setNotes(newPrayerAndTurn.getNotas());
				newTurn.setPax(1);
				newTurn.setStatus(TurnStatus.accepted);
				newTurn.setTurn(newPrayerAndTurn.getTurno());
				newTurn.setPrayer(newPrayer);
				List<TurnEntity> turnos = new ArrayList<TurnEntity>();
				turnos.add(newTurn);
				newPrayer.setTurns(turnos);
				
				prayerService.addPrayer(newPrayer);

			} else {
				error = 2;
			}
			
		} else {
			//Se encontraron oradores con el mismo email
			error = 1;
		}
		
		return error;
	}	
	
	@RequestMapping(path="/getPrayerAndTurns.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getPrayerAndTurns(@RequestBody int prayerID) throws IOException, DDBBException{
		PrayerEntity prayer = prayerService.getPrayer(prayerID);
		if (prayer==null) throw new RuntimeException("El orador con ID n." + prayerID + " no existe en la base de datos");
		List<TurnEntity> turns = prayer.getTurns();
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("Prayer", prayer);
		if (turns!=null) result.put("turns", turns);
		return result;
	}
	
	@RequestMapping(path="/updatePrayer.do", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updatePrayer(@RequestBody PrayerEntity prayer){
		if (prayer!=null){
			return prayerService.updatePrayer(prayer);
		}
		return true;
	}

	@RequestMapping(path="/updateTurn.do", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateTurn(@RequestBody TurnEntity turn){
		if (turn!=null){
			return prayerService.updateTurn(turn);
		}
		return true;
	}

	@RequestMapping(path="/addTurn.do", method=RequestMethod.POST)
	@ResponseBody
	public Boolean addTurn(@RequestBody TurnEntity turn){
		if (turn!=null){
			return turnService.addTurn(turn);
		}
		return true;
	}
}