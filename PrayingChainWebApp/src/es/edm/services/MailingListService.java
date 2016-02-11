package es.edm.services;

import org.springframework.web.context.request.WebRequest;

public interface MailingListService {

	public void processRequest(WebRequest request);
	public void processSubscribe(WebRequest request);
	public void processUnsubscribe(WebRequest request);
	public void processProfile(WebRequest request);
	public void processEmailChange(WebRequest request);
	public void processCleanedEmail(WebRequest request);
	public void processCampaign(WebRequest request);
}
