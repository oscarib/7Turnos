package es.edm.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import es.edm.services.Configuration;
import es.edm.services.MailingListService;

@Component
public class MailingListService_Mailchimp_Impl implements MailingListService {

	@Autowired
	Configuration conf;
	
	@Override
	public void processRequest(WebRequest request) {
		if (request.getParameter("secret")!= null){
			if (request.getParameter("secret").equals(conf.getMailingListSecretPassword())){
				switch (request.getParameter("type")){
				case "subscribe": processSubscribe(request); break;
				case "unsubscribe": processUnsubscribe(request); break;
				case "profile": processProfile(request); break;
				case "upemail": processEmailChange(request); break;
				case "cleaned": processCleanedEmail(request); break;
				case "campaign": processCampaign(request); break;
				}
			}
		}
	}

	@Override
	public void processSubscribe(WebRequest request) {
		System.out.println("Received a subscribe request");
	}

	@Override
	public void processUnsubscribe(WebRequest request) {
		System.out.println("Received an unsubscribe request");
	}

	@Override
	public void processProfile(WebRequest request) {
		System.out.println("Received a profile updating request");
	}

	@Override
	public void processEmailChange(WebRequest request) {
		System.out.println("Received an email changing request");
	}

	@Override
	public void processCleanedEmail(WebRequest request) {
		System.out.println("Received an email cleaning request");
	}

	@Override
	public void processCampaign(WebRequest request) {
		System.out.println("Received a campaign has been sent advice");
	}

}
