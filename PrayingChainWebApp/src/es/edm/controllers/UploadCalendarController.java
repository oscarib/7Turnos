package es.edm.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.exceptions.DDBBException;
import es.edm.services.FileService;
import es.edm.services.MainService;
import es.edm.services.helpers.Configuration;

@Controller
public class UploadCalendarController {

	@Autowired
	MainService main;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	Configuration conf;

	@RequestMapping(path="/uploadCalendar", method=RequestMethod.GET)
	public ModelAndView UploadCalendar() throws IOException, DDBBException{
		try{
			fileService.WriteFile(main.getCalendarTableString(), conf.getCalendarFile2UploadURI());
			fileService.WriteFile(main.getStatisticsString(), conf.getStatisticsFile2UploadURI());
			fileService.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
			fileService.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
		} catch (UnknownHostException e){
			e.printStackTrace();
			return new ModelAndView("/web/HostNotReacheable.jsp");
		} catch (SocketException e) {
			e.printStackTrace();
			return new ModelAndView("/web/HostNotReacheable.jsp");
		}
		return new ModelAndView("/web/UploadCalendar.jsp");
	}
}
