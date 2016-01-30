package es.edm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping(path="/index", method=RequestMethod.GET)
	public ModelAndView showBootstrap(){
		Runtime runtime = Runtime.getRuntime();
		double usedMemory = runtime.totalMemory()-runtime.freeMemory();
		System.out.println("Used Memory before calling the Garbage Collector: " + usedMemory/(1024*1024));
		runtime.gc();
		usedMemory = runtime.totalMemory()-runtime.freeMemory();
		System.out.println("Used Memory AFTER calling the Garbage Collector: " + usedMemory/(1024*1024));
		return new ModelAndView("/web/index.jsp");
	}
}
