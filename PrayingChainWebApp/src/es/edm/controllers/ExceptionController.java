package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionController {

	@RequestMapping(path="/JDBCException", method=RequestMethod.GET)
	public ModelAndView JdbcConnectionErrorGet(){
		return new ModelAndView ("/web/JDBCException.jsp");
	}

	@RequestMapping(path="/JDBCException", method=RequestMethod.POST)
	public ModelAndView JdbcConnectionErrorPost(){
		return new ModelAndView ("/web/JDBCException.jsp");
	}
}
