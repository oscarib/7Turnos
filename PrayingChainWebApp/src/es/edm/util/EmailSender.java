package es.edm.util;

import java.util.List;

import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	String userName;
	String userPassword;
	String hostName;
	int smtpPort;
	boolean ssl;
	Email email;
	
	public EmailSender(String userName, String hostName, String password, int smtpPort, boolean ssl){
		this.userName = userName;
		this.userPassword = password;
		this.hostName = hostName;
		this.smtpPort = smtpPort;
		this.ssl = ssl;
		this.email = new SimpleEmail();
	}
	
	public void sendEmail(String subject, String message, 
						  InternetAddress sender, 
						  List<InternetAddress> recipients,
						  List<InternetAddress> recipientsCC,
						  List<InternetAddress> recipientsCCO) throws EmailException{

		email.setHostName(hostName);
		email.setSmtpPort(smtpPort);
		email.setAuthenticator(new DefaultAuthenticator(userName, userPassword));
		email.setSSLOnConnect(ssl);
		setFrom(sender);
		setRecipients(recipients);
		setRecipientsCC(recipientsCC);
		setRecipientsCCO(recipientsCCO);
		email.setSubject(subject);
		email.setMsg(message);
		email.send();
	}
	
	public void setFrom(InternetAddress sender) throws EmailException{
		if (sender.getPersonal()!=null || !sender.getPersonal().equals("")){
			email.setFrom(sender.getAddress(), sender.getPersonal());
		} else {
			email.setFrom(sender.getAddress());
		}
	}
	
	public void setRecipients(List<InternetAddress> recipients) throws EmailException{
		if (recipients!=null && recipients.size()>0){
			for (InternetAddress nextRecipient : recipients){
				setTo(nextRecipient);
			}
		} else {
			throw new RuntimeException("No recipient set for the email. At least, 1 should be set");
		}
	}

	public void setRecipientsCC(List<InternetAddress> recipientsCC) throws EmailException{
		if (recipientsCC!=null && recipientsCC.size()>0){
			for (InternetAddress nextRecipient : recipientsCC){
				setToCC(nextRecipient);
			}
		}
	}

	public void setRecipientsCCO(List<InternetAddress> recipientsCCO) throws EmailException{
		if (recipientsCCO!=null && recipientsCCO.size()>0){
			for (InternetAddress nextRecipient : recipientsCCO){
				setToCCO(nextRecipient);
			}
		}
	}

	private void setTo(InternetAddress recipient) throws EmailException{
		if (recipient.getPersonal()!=null && !recipient.getPersonal().equals("")){
			email.addTo(recipient.getAddress(), recipient.getPersonal());
		} else {
			email.addTo(recipient.getAddress());
		}
	}
	
	private void setToCC(InternetAddress recipient) throws EmailException{
		if (recipient.getPersonal()!=null && !recipient.getPersonal().equals("")){
			email.addCc(recipient.getAddress(), recipient.getPersonal());
		} else {
			email.addCc(recipient.getAddress());
		}
	}
	
	private void setToCCO(InternetAddress recipient) throws EmailException{
		if (recipient.getPersonal()!=null && !recipient.getPersonal().equals("")){
			email.addBcc(recipient.getAddress(), recipient.getPersonal());
		} else {
			email.addBcc(recipient.getAddress());
		}
	}
}
