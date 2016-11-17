package es.edm.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import es.edm.services.IOtherServices;
import es.edm.util.EmailSender;

@Controller
@RequestMapping(path="/Webhook")
public class MailchimpController {
	
	@Autowired
	IOtherServices otherServices;
	
	/* Mailchimp Webhook URLs
	 * 		Local: http://prayingchain.ddns.net:8787/PrayingChainWebApp/Webhook.html?secret=arkanoid
	 * 		Jelastic: http://prayingchain.jelastic.cloudhosted.es/Webhook.html?secret=arkanoid
	 */
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getProcess(WebRequest request){
		System.out.println(new Date() + ": Detected a Mailchimp GET request...");
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView postProcess(WebRequest request){
		
		Configuration conf = otherServices.getConfiguration();
		
		//Let's get the Mailchimp request Data
		Map<String,String> mailchimpData = getMailchimpData(request);
		
		/* Creates a new instance of EmailSender
		 * We do not use Spring dependency Injection here because we don't need it
		 */
		String userName = conf.getEmailServiceUserName();
		String hostName = conf.getEmailServiceHostName();
		String password = conf.getEmailServiceUserPassword();
		int smtpPort = conf.getEmailServiceSmtpPort();
		boolean ssl=conf.isEmailServiceSSL();
		EmailSender emailSender = new EmailSender(userName, hostName, password, smtpPort, ssl);

		if (request.getParameter("secret")!= null){
			if (request.getParameter("secret").equals(conf.getMailchimpUrlPwd())){

				try{
					//Depending on request type...
					switch (request.getParameter("type")){
					case "subscribe": processSubscribe(mailchimpData); break;
					case "unsubscribe": processUnsubscribe(mailchimpData); break;
					case "profile": processProfile(mailchimpData, emailSender); break;
					case "cleaned": processCleanedEmail(mailchimpData, emailSender); break;
					}
				} catch (EmailException e){
					System.out.println("Something went really wrong while trying to send an email...");
				}
			}
		}
		printFooter();
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}

	public void processSubscribe(Map<String,String> mailchimpData) {
		//Nothing is needed here, as Mailchimp already sends a warning email with this by its own
	}

	public void processUnsubscribe(Map<String,String> mailchimpData) {
		//Nothing is needed here, as Mailchimp already sends a warning email with this by its own
	}

	public void processProfile(Map<String,String> mailchimpData, EmailSender emailSender) throws EmailException {
		printHeader("Profile Update");
		sendWarningEmail(mailchimpData, emailSender);
	}

	public void processEmailChange(Map<String,String> mailchimpData) {
		//Nothing is needed to be done, as this process is already caught by "profile change request" also.
	}

	public void processCleanedEmail(Map<String,String> mailchimpData, EmailSender emailSender) throws EmailException {
		printHeader("Email cleaning");
		sendWarningEmail(mailchimpData, emailSender);
	}

	// Sends a warning email to a predefined email address.
	//TODO: Change hardcoded email addresses...
	private void sendWarningEmail(Map<String,String> mailchimpData, EmailSender emailSender) throws EmailException{
		
		//Logs the reuqest type to System.out
		System.out.println(new Date() + ": Requester is " + mailchimpData.get("email"));
		
		//Sets the email subject
		String subject = "Received a MailChimp '" + mailchimpData.get("type") + "' advice from " + mailchimpData.get("name");
		
		//Sets the message
		String message  = new Date() + ": Received a MailChimp '" + mailchimpData.get("type") + "' advice from " + 
						  mailchimpData.get("name") + " (Email: " + mailchimpData.get("email") + ")";
		
		//Something could go wrong while sending the email...
		try {

			//Sets the sender
			InternetAddress sender;
			sender = new InternetAddress("espanademaria@gmail.com", "Oscar Ib");
			emailSender.setSender(sender);
			
			//and the recipients...
			List<InternetAddress> recipients = new ArrayList<InternetAddress>();
			recipients.add(new InternetAddress("oscar.ibafer@gmail.com", "Oscar Ib"));

			emailSender.setSubject(subject);
			emailSender.setMessage(message);
			emailSender.setRecipients(recipients);

			//Logging that we're gonna send an email...
			System.out.println(new Date() + ": Sending a warning email to oscar.ibafer@gmail.com...");

			//Starts a new thread to send the email asynchronously
			emailSender.start();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException ("Something went really wrong while trying to send an email");
		}
	}
	
	private void printHeader(String requestType){
		System.out.println(new Date() + ": Received a MailChimp '" + requestType + "' advice. Processing...");
	}
	
	private void printFooter(){
		System.out.println(new Date() + ": Mailchimp request has been answered");
	}
	
	private Map<String,String> getMailchimpData(WebRequest request){
		Map <String, String> mailchimpData = new LinkedHashMap<String, String>();
		setMailchimpParameter("type", "type", request, mailchimpData);
		setMailchimpParameter("email", "data[merges][EMAIL]", request, mailchimpData);
		setMailchimpParameter("name", "data[merges][FNAME]", request, mailchimpData);
		setMailchimpParameter("dow", "data[merges][MMERGE8]", request, mailchimpData);
		setMailchimpParameter("turn", "data[merges][MMERGE7]", request, mailchimpData);
		setMailchimpParameter("country", "data[merges][MMERGE6]", request, mailchimpData);
		setMailchimpParameter("phone", "data[merges][TELEFONO]", request, mailchimpData);
		setMailchimpParameter("pseudonym", "data[merges][MMERGE4]", request, mailchimpData);
		setMailchimpParameter("hidden", "data[merges][MMERGE2]", request, mailchimpData);
		setMailchimpParameter("notes", "data[merges][NOTAS]", request, mailchimpData);
		return mailchimpData;
	}
	
	private void setMailchimpParameter(String key, String value, WebRequest request, Map<String,String> mailchimpData){
		if (request.getParameter(value)!=null){
			mailchimpData.put(key, request.getParameter(value));
		}
	}
}
