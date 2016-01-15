package es.edm.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.edm.services.MainService;

@Controller
public class TurnsController {

	@Autowired
	private MainService main;
	
	@RequestMapping("/deleteTurn")
	public ModelAndView deleteTurn(){
		//TODO: Write Controller deleteTurn()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/changeTurn")
	public ModelAndView changeTurn(){
		//TODO: Write Controller changeTurn()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/addTurn2Prayer")
	public ModelAndView addTurn2Prayer(){
		//TODO: Write Controller addTurn2Prayer()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
	
	@RequestMapping("/addTurn")
	public ModelAndView addTurn(){
		//TODO: Write Controller addTurn()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
}
