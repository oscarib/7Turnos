package es.edm.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfigurationController {

	@RequestMapping("/changeConfiguration")
	public ModelAndView changeConfiguration(){
		//TODO: Write Controller changeConfiguration()
		return new ModelAndView("/web/UnderDevelopment.jsp");
	}
}
