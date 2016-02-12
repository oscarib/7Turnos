package es.edm.util;

import java.util.Date;

import org.springframework.web.context.request.WebRequest;

import es.edm.services.MailingListService;

public class MailingListRequestProcessor implements Runnable {
	
	private Thread t;
	private WebRequest request;
	private MailingListService mailing;
	
	public MailingListRequestProcessor(WebRequest request, MailingListService mailing){
		this.request = request;
		this.mailing = mailing;
		System.out.println(new Date() + ": Received a MailChimp Request. Looking for request type...");
	}
	
	@Override
	public void run() {
		mailing.processRequest(request);
		System.out.println(new Date() + ": Mailchimp Request has been succesfully processed.");
	}

	public void start () {
		if (t == null)
		{
			t = new Thread (this, "MailchimpRequest");
			t.start ();
		}
	}
}
