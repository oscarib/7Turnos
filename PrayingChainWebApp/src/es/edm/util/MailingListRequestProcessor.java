package es.edm.util;

import org.springframework.web.context.request.WebRequest;

import es.edm.services.MailingListService;

public class MailingListRequestProcessor implements Runnable {
	
	private Thread t;
	private WebRequest request;
	private MailingListService mailing;
	
	@Override
	public void run() {
		mailing.processRequest(request);
	}

	public void start (WebRequest request, MailingListService mailing) {
		if (t == null){
			this.request = request;
			this.mailing = mailing;
			t = new Thread (this, "MailchimpRequest");
			t.start ();
		}
	}
}
