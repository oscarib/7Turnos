package es.edm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.Prayer;
import es.edm.services.MainService;

@Controller
public class getAllPrayers {

	@Autowired
	private MainService main;
	
	@ResponseBody
	@RequestMapping(value = "/getAllPrayers.do", method = RequestMethod.POST)
	public List<Prayer> getAllPrayerList() {
		Prayer prayer = new Prayer(1,"Oscar", "oscar.ibafer@gmail.com","917052323",true,new Date(),"No notes",false,"seudonimo");
		List<Prayer> prayerList = new ArrayList<Prayer>();
		prayerList.add(prayer);
		//return prayerList;
		return main.getAllPrayers();
	}
}