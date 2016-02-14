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
		//To recover, once solved the problem with Jelastic and getParameters();
//		if (request.getParameter("secret")!= null){
//			if (request.getParameter("secret").equals(conf.getMailingListSecretPassword())){
				switch (request.getParameter("type")){
				case "subscribe": processSubscribe(request); break;
				case "unsubscribe": processUnsubscribe(request); break;
				case "profile": processProfile(request); break;
				case "upemail": processEmailChange(request); break;
				case "cleaned": processCleanedEmail(request); break;
				case "campaign": processCampaign(request); break;
				}
//			}
//		}
	}

	@Override
	public void processSubscribe(WebRequest request) {
		//Nothing is needed here, as Mailchimp already sends a warning email with this by its own
	}

	@Override
	public void processUnsubscribe(WebRequest request) {
		//Nothing is needed here, as Mailchimp already sends a warning email with this by its own
	}

	@Override
	public void processProfile(WebRequest request) {
		printHeader("Profile Update");
		sendWarningEmail(request, "Profile Update");
		printFooter();
	}

	@Override
	public void processEmailChange(WebRequest request) {
		//Nothing is needed to be done, as this process is already caught by "profile change request" also.
	}

	@Override
	public void processCleanedEmail(WebRequest request) {
		printHeader("Email cleaning");
		sendWarningEmail(request, "Cleaned Email");
		printFooter();
	}

	@Override
	public void processCampaign(WebRequest request) {
		printHeader("Campaign sending");
		System.out.println(new Date() + ": The campaign called '" + request.getParameter("data[subject]") + "' has now been sent to " + request.getParameter("data[merges][EMAIL]"));
		printFooter();
	}
	
	// Sends a warning email to a predefined email address.
	//TODO: Change hardcoded email addresses...
	private void sendWarningEmail(WebRequest request, String requestType){
		
		//Logs the reuqest type to System.out
		System.out.println(new Date() + ": Requester is " + request.getParameter("data[merges][EMAIL]"));
		
		//Sets the email subject
		String subject = "A new profile update has been made by " + request.getParameter("data[merges][FNAME]");
		
		//Sets the message
		String message  = new Date() + ": A new profile update has been made by " + request.getParameter("data[merges][FNAME]") + 
						  " (Email: " + request.getParameter("data[merges][EMAIL]") + ")";
		
		InternetAddress sender;

		//Something could go wrong while sending the email...
		try {
			//Sets the sender
			sender = new InternetAddress("espanademaria@gmail.com", "Oscar Ib");
			
			//and the recipients...
			List<InternetAddress> recipients = new ArrayList<InternetAddress>();
			recipients.add(new InternetAddress("oscar.ibafer@gmail.com", "Oscar Ib"));

			//Logging that we're gonna send an email...
			System.out.println(new Date() + ": Sending a warning email to oscar.ibafer@gmail.com...");

			try {
				emailSender.sendEmail(subject, message, sender, recipients, null, null);
			} catch (EmailException e) {
				e.printStackTrace();
				throw new RuntimeException ("Something went really wrong while trying to send an email");
			}
			System.out.println(new Date() + ": And email has been sent to provided recipients");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException ("Something went really wrong while trying to send an email");
		}
	}
	
	private void printHeader(String requestType){
		System.out.println(new Date() + ": Received a MailChimp '" + requestType + "' advice. Processing...");
	}
	
	private void printFooter(){
		System.out.println(new Date() + ": Mailchimp advice has been succesfully processed.");
		System.out.println("");
	}
}
