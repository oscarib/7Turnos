package es.edm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import es.edm.services.MailingListService;

@Controller
@RequestMapping(path="/Webhook")
public class MailingServiceIntegrationController_MailchimpImpl {
	
	@Autowired
	MailingListService mailing;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getProcess(WebRequest request){
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView postProcess(WebRequest request){
		mailing.processRequest(request);
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}
}
