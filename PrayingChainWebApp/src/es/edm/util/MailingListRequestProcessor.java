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
	}
	
	@Override
	public void run() {
		mailing.processRequest(request);
	}

	public void start () {
		if (t == null)
		{
			t = new Thread (this, "MailchimpRequest");
			t.start ();
		}
	}
}
