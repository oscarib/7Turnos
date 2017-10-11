package es.edm.controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class PrayerController {

    @Autowired
    Configuration conf;
    @Autowired
    private IPrayerService prayerService;
    @Autowired
    private ITurnService turnService;
    @Autowired
    private IOtherServices otherServices;
    @Value("${label_ownCountry}")
    String labelOwnCountry;
    @Value("${label_hidden}")
    String hiddenString;

    @ResponseBody
    @RequestMapping(value = "/getAllPrayers.do", method = RequestMethod.POST)
    public List<PrayerEntity> getAllPrayerList() {
        List<PrayerEntity> prayers2Return = prayerService.getPrayers();
        return prayers2Return;
    }

    @ResponseBody
    @RequestMapping(value = "/getOrphanPrayers.do", method = RequestMethod.POST)
    public List<PrayerEntity> getOrphanPrayers() {
        List<PrayerEntity> prayers2Return = prayerService.getOrphanPrayers();
        return prayers2Return;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllTurns.do", method = RequestMethod.POST)
    public List<TurnEntity> getAllTurns() {
        List<TurnEntity> turns2Return = turnService.getAllTurns();
        return turns2Return;
    }

    /* Comprueba si existen más oradores con el mismo email y teléfono
     * En caso contrario, crea el orador en ddbb
     * Códigos de retorno:
     * 0: Se crea el orador
     * 1: No se pudo crear el orador, porque ya existe otro con el mismo email
     * 2: No se pudo crear el orador, porque ya existe otro orador con el mismo teléfono
     * 3: Se recupera un orador borrado con anterioridad*/
    @ResponseBody
    @RequestMapping(path = "/createNewPrayer.do", method = RequestMethod.POST)
    public int createNewPrayer(@RequestBody NewPrayerAndTurn newPrayerAndTurn) throws IOException, DDBBException {

        int error = 0;
        PrayerEntity interPrayer = new PrayerEntity();
        List<PrayerEntity> foundPrayers;
        PrayerEntity foundPrayer = null;
        PrayerEntity newPrayer;
        interPrayer.setEmail(newPrayerAndTurn.getEmail());
        interPrayer.setPhone(newPrayerAndTurn.getTelefono());


        //Comprobamos si existe un orador con los mismos datos en la ddbb
        foundPrayers = prayerService.getPrayersByEmail(interPrayer, true);
        if (!foundPrayers.isEmpty()) {
            foundPrayer = findPrayer(foundPrayers, newPrayerAndTurn);
        }

        boolean isErased = foundPrayer != null && foundPrayer.isErased();
        if (isErased) {
            copyPrayerData(foundPrayer, newPrayerAndTurn);
            updatePrayer(foundPrayer);
            return 3;
        }

        if (foundPrayer == null) {

            //Comprobamos si existe ese teléfono
            foundPrayers = prayerService.getPrayersByPhone(interPrayer, true);
            if (!foundPrayers.isEmpty()) {
                foundPrayer = findPrayer(foundPrayers, newPrayerAndTurn);
            }

            isErased = foundPrayer != null && foundPrayer.isErased();
            if (isErased) {
                copyPrayerData(foundPrayer, newPrayerAndTurn);
                updatePrayer(foundPrayer);
                return 3;
            }

            if (foundPrayer == null) {

                //Creación del orador
                newPrayer = new PrayerEntity();
                newPrayer.setName(newPrayerAndTurn.getNombre());
                newPrayer.setEmail(newPrayerAndTurn.getEmail());
                newPrayer.setPhone(newPrayerAndTurn.getTelefono());
                newPrayer.setChain(otherServices.getLoggedUser().getChain());
                boolean ownCountry = (labelOwnCountry.equals(newPrayerAndTurn.getPais()) ? true : false);
                newPrayer.setOwnCountry(ownCountry);
                newPrayer.setOptinDate(new Date());
                newPrayer.setNotes(newPrayerAndTurn.getNotas());
                newPrayer.setPseudonym(newPrayerAndTurn.getSeudonimo());
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
            error = 1;
        }

        return error;
    }

    @RequestMapping(path = "/getPrayerAndTurns.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPrayerAndTurns(@RequestBody int prayerID) throws IOException, DDBBException {
        PrayerEntity prayer = prayerService.getPrayer(prayerID);
        if (prayer == null)
            throw new RuntimeException("El orador con ID n." + prayerID + " no existe en la base de datos");
        List<TurnEntity> turns = prayer.getTurns();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("Prayer", prayer);
        if (turns != null) result.put("turns", turns);
        return result;
    }

    @RequestMapping(path = "/updatePrayer.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updatePrayer(@RequestBody PrayerEntity prayer) {
        if (prayer != null) {
            return prayerService.updatePrayer(prayer);
        }
        return true;
    }

    @RequestMapping(path = "/updateTurn.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateTurn(@RequestBody TurnEntity turn) {
        if (turn != null) {
            return prayerService.updateTurn(turn);
        }
        return true;
    }

    @RequestMapping(path = "/addTurn.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addTurn(@RequestBody TurnEntity turn) {
        if (turn != null) {
            return turnService.addTurn(turn);
        }
        return true;
    }

    private void copyPrayerData(PrayerEntity foundPrayer, NewPrayerAndTurn newData) {
        foundPrayer.setChain(otherServices.getLoggedUser().getChain());
        foundPrayer.setEmail(newData.getEmail());
        foundPrayer.setErased(false);
        foundPrayer.setHidden(hiddenString.equals(newData.getVisibilidad()) ? true : false);
        foundPrayer.setName(newData.getNombre());
        foundPrayer.setNotes(newData.getNotas());
        boolean ownCountry = (labelOwnCountry.equals(newData.getPais()) ? true : false);
        foundPrayer.setOwnCountry(ownCountry);
        foundPrayer.setPhone(newData.getTelefono());
        foundPrayer.setPseudonym(newData.getSeudonimo());
    }

    private PrayerEntity findPrayer(List<PrayerEntity> prayersList, NewPrayerAndTurn prayer2Find) {
        for (PrayerEntity prayer : prayersList) {
            if (prayer2Find.getNombre().equals(prayer.getName()) &&
                    prayer2Find.getEmail().equals(prayer.getEmail()) &&
                    prayer2Find.getTelefono().equals(prayer.getPhone()) &&
                    otherServices.getLoggedUser().getChain() == prayer.getChain()) {
                return prayer;
            }
        }
        return null;
    }
}