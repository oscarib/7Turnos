package es.edm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.entity.PrayerEntity;
import es.edm.services.IPrayerService;

@Controller
public class getAllPrayers {

	@Autowired
	private IPrayerService prayerService;
	
	@ResponseBody
	@RequestMapping(value = "/getAllPrayers.do", method = RequestMethod.POST)
	public List<PrayerEntity> getAllPrayerList() {
		List<PrayerEntity> prayers2Return = prayerService.getPrayers();
		return prayers2Return;
	}
}