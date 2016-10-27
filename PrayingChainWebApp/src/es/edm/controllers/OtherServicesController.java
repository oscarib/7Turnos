package es.edm.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.MainService;
import es.edm.services.Impl.PrayerService;
import es.edm.services.Impl.TurnService;

@Controller
public class OtherServicesController {

	@Autowired
	MainService main;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	PrayerService prayerService;
	
	@Autowired
	TurnService turnService;
	
	@Autowired
	Configuration conf;

	//CONTROLLER: UPLOADCALENDAR
	@RequestMapping(path="/uploadCalendar.do", method=RequestMethod.POST)
	@ResponseBody
	public boolean UploadCalendar() throws IOException, DDBBException{
		try{
			fileService.WriteFile(main.getCalendarTableString(), conf.getCalendarFile2UploadURI());
			fileService.WriteFile(main.getStatisticsString(), conf.getStatisticsFile2UploadURI());
			fileService.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
			fileService.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
		} catch (UnknownHostException e){
			throw new RuntimeException("Error: " + e);
		} catch (SocketException e) {
			throw new RuntimeException("Error: " + e);
		}
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
		statistics.put("HiddenPrayers", prayerService.getHiddenPrayers().size());
		statistics.put("PublicPrayers",prayerService.getPublicPrayers().size());
		statistics.put("ForeignPrayers", prayerService.getForeignPrayers().size());
		statistics.put("LocalPrayers", prayerService.getLocalPrayers().size());
		statistics.put("OrphanPrayers", prayerService.getOrphanPrayers().size()); //TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		
		return statistics;
	}
}
