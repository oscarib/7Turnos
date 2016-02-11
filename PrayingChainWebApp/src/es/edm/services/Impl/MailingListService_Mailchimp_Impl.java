package es.edm.services.Impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import es.edm.services.Configuration;
import es.edm.services.MailingListService;
import es.edm.util.EmailSender;

@Component
public class MailingListService_Mailchimp_Impl implements MailingListService {

	/* URL for Mailchimp: http://prayingchain.ddns.net:8787/PrayingChainWebApp/Webhook.html?secret=arkanoid
	 * 
	 */
	
	@Autowired
	Configuration conf;
	
	@Autowired
	EmailSender emailSender;
	
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
		System.out.println("A profile update has been caught for " + request.getParameter("data[merges][FNAME]"));
		String subject = "A new profile update has been made by " + request.getParameter("data[merges][FNAME]");
		String message  = new Date() + ": A new profile update has been made by " + request.getParameter("data[merges][FNAME]") + 
						  " (Email: " + request.getParameter("data[merges][EMAIL]") + ")";
		InternetAddress sender;
		try {
			sender = new InternetAddress("oscar.ibafer@gmail.com", "Oscar Ib");
			List<InternetAddress> recipients = new ArrayList<InternetAddress>();
			recipients.add(new InternetAddress("oscar.ibafer@gmail.com", "Oscar Ib"));
			try {
				emailSender.sendEmail(subject, message, sender, recipients, null, null);
			} catch (EmailException e) {
				e.printStackTrace();
				throw new RuntimeException ("Something went really wrong while trying to send an email");
			}
			System.out.println(new Date() + ": And email has been sent to " + sender.getAddress());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException ("Something went really wrong while trying to send an email");
		}
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
		System.out.println("Mailchimp informs that a campaign called " + request.getParameter("data[subject]") + " has been sent to " + request.getParameter("data[merges][FNAME]"));
	}

}
