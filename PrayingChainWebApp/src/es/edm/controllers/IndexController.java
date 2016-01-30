package es.edm.controllers;

import java.text.DecimalFormat;

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
		String usedMemoryString = new DecimalFormat("#.##").format(usedMemory/(1024*1024));
		System.out.println("Used Memory before calling the Garbage Collector: " + usedMemoryString + "Mb");
		runtime.gc();
		usedMemory = runtime.totalMemory()-runtime.freeMemory();
		usedMemoryString = new DecimalFormat("#.##").format(usedMemory/(1024*1024));
		System.out.println("Used Memory before calling the Garbage Collector: " + usedMemoryString + "Mb");
		return new ModelAndView("/web/index.jsp");
	}
}
