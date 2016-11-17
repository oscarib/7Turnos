package es.edm.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.Impl.PrayerService;
import es.edm.services.Impl.TurnService;

@RestController
public class OtherServicesController {

	@Autowired
	FileService fileService;
	
	@Autowired
	PrayerService prayerService;
	
	@Autowired
	TurnService turnService;
	
	@Autowired
	Configuration conf;
	
	private final static Logger logger = LoggerFactory.getLogger(OtherServicesController.class);

	//CONTROLLER: UPLOADCALENDAR
	@RequestMapping(path="/uploadCalendar.do", method=RequestMethod.POST)
	@ResponseBody
	public boolean UploadCalendar() throws IOException, DDBBException{
		try{
			fileService.WriteFile(fileService.getCalendarTableString(1), conf.getCalendarFile2UploadURI());
			logger.info("Generando el archivo del calendario...");
			fileService.WriteFile(fileService.getStatisticsString(), conf.getStatisticsFile2UploadURI());
			logger.info("Generando el archivo de estadísticas...");
			fileService.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
			logger.info("Subiendo los archivos al sitio FTP...");
			fileService.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
		} catch (UnknownHostException e){
			throw new RuntimeException("Error: " + e);
		} catch (SocketException e) {
			throw new RuntimeException("Error: " + e);
		}
		logger.info("Se subieron los archivos con éxito al sitio FTP.");
		return true;
	}
	
	//CONTROLLER: GETSTATISTICS
	@ResponseBody
	@RequestMapping(value = "/getStatistics.do", method = RequestMethod.POST)
	public Map<String,Object> getallStatistics() {
		
		HashMap<String,Object> statistics;
		
		statistics = new HashMap<String, Object>();
		int totalTurns = turnService.getTotalTurns();
		int daysCovered = turnService.getDaysCovered();
		int usedTurns = turnService.getUsedTurns();
		statistics.put("TotalTurns", totalTurns);
		int availableTurns = totalTurns-usedTurns;
		int availableDays = (totalTurns/conf.getPrayersPerTurn())-daysCovered;
		statistics.put("AvailableTurns", availableTurns);
		statistics.put("EmptyTurns", turnService.getEmptyTurns());
		statistics.put("UsedTurns", usedTurns);
		statistics.put("DaysCovered", daysCovered);
		statistics.put("availableDays", availableDays);
		int committedPrayers = prayerService.getCommittedPrayers().size();
		int nonCommittedPrayers = prayerService.getPrayers().size() - committedPrayers;
		statistics.put("CommittedPrayers", committedPrayers);
		statistics.put("NonCommittedPrayers", nonCommittedPrayers);
		int totalPrayers = committedPrayers+nonCommittedPrayers;
		statistics.put("TotalPrayers", totalPrayers);
		statistics.put("HiddenPrayers", prayerService.getHiddenPrayers().size());		//TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		statistics.put("PublicPrayers",prayerService.getPublicPrayers().size());		//TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		statistics.put("ForeignPrayers", prayerService.getForeignPrayers().size());		//TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		statistics.put("LocalPrayers", prayerService.getLocalPrayers().size());   		//TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		statistics.put("OrphanPrayers", prayerService.getOrphanPrayers().size()); 		//TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		
		return statistics;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCalendarHtml.do", method = RequestMethod.POST)
	public List<String> getCalendarString(){
		List<String> calendar = new ArrayList<String>();
		String calendarString = fileService.getCalendarTableString(4);
		calendar.add(calendarString);
		return calendar;
	}
}