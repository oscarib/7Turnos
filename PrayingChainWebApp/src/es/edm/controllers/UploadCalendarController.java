package es.edm.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.MainService;

@Controller
public class UploadCalendarController {

	@Autowired
	MainService main;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	Configuration conf;

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
}
