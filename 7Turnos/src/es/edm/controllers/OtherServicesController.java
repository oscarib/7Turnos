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

import es.edm.domain.entity.StatisticsEntity;
import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.IOtherServices;
import es.edm.services.IPrayerService;
import es.edm.services.ITurnService;

@RestController
public class OtherServicesController {

	@Autowired
	FileService fileService;
	
	@Autowired
	IPrayerService prayerService;
	
	@Autowired
	ITurnService turnService;
	
	@Autowired
	IOtherServices otherServices;
	
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
	public StatisticsEntity getallStatistics() {
		
		StatisticsEntity statistics = otherServices.getStatistics();
		
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
