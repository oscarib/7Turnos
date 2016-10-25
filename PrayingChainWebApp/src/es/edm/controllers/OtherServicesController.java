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

@Controller
public class OtherServicesController {

	@Autowired
	MainService main;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	PrayerService prayerService;
	
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
		statistics.put("TotalTurns", main.getTotalTurns());
		statistics.put("TurnsCovered", main.getUsedTurns());
		int availableTurns = main.getTotalTurns()-main.getUsedTurns();
		statistics.put("AvailableTurns", availableTurns);
		statistics.put("EmptyTurns", main.getEmptyTurns());
		int committedPrayers = main.getNumberOfCommittedPrayers();
		int nonCommittedPrayers = main.getNumberOfNonCommittedPrayers();
		statistics.put("CommittedPrayers", committedPrayers);
		statistics.put("NonCommittedPrayers", nonCommittedPrayers);
		int totalPrayers = committedPrayers+nonCommittedPrayers;
		statistics.put("TotalPrayers", totalPrayers);
		statistics.put("TurnsUsedPercentage", main.getTurnsUsedPercentage());
		statistics.put("FreeTurnsPercentage", main.getFreeTurnsPercentage());
		statistics.put("EmptyTurnsPercentage", main.getEmptyTurnsPercentage());
		statistics.put("CommittedRedundancy", main.getCommittedRedundancy());
		statistics.put("TotalRedundancy",  main.getTotalRedundancy());
		statistics.put("HiddenPrayers", prayerService.getHiddenPrayers().size());
		statistics.put("PublicPrayers",prayerService.getPublicPrayers().size());
		statistics.put("ForeignPrayers", prayerService.getForeignPrayers().size());
		statistics.put("LocalPrayers", prayerService.getLocalPrayers().size());
		statistics.put("OrphanTurns", main.getOrphanTurns().size());
		statistics.put("OrphanPrayers", main.getOrphanPrayers().size()); //TODO: Crear vista para no tener que traerse todos los oradores y contarlos
		
		return statistics;
	}
}
