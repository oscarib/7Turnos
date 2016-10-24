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
public class getPrayerAndTurns {

	@Autowired
	MainService main;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	Configuration conf;

	@RequestMapping(path="/getPrayerAndTurns.do", method=RequestMethod.POST)
	@ResponseBody
	public Object UploadCalendar() throws IOException, DDBBException{
		
	}
}
