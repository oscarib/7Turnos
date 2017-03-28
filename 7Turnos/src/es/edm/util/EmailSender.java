package es.edm.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.List;

@Component
public class EmailSender extends Thread {

    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);
    String userName;
    String userPassword;
    String hostName;
    int smtpPort;
    boolean ssl;
    Email email;
    String subject;
    String message;
    InternetAddress sender;
    List<InternetAddress> recipients;
    List<InternetAddress> recipientsCC;
    List<InternetAddress> recipientsCCO;
    Thread t;

    public EmailSender(String userName, String hostName, String password, int smtpPort, boolean ssl) {
        this.userName = userName;
        this.userPassword = password;
        this.hostName = hostName;
        this.smtpPort = smtpPort;
        this.ssl = ssl;
        this.email = new SimpleEmail();
        t = new Thread(this);
    }

    @Override
    public void run() {
        sendEmail();
    }

    private void sendEmail() {

        email.setHostName(hostName);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(new DefaultAuthenticator(userName, userPassword));
        email.setSSLOnConnect(ssl);
        try {
            if (sender == null) {
                throw new RuntimeException("You should set the email sender by using setSender(InternetAddress sender)");
            } else {
                setFrom(sender);
            }
            if (recipients == null) {
                throw new RuntimeException("You should set at least one recepient for the email by using setRecipients(List<InternetAddress> recipìents)");
            } else {
                setRecipients(recipients);
            }
            if (recipientsCC != null) {
                setRecipientsCC(recipientsCC);
            }
            if (recipientsCCO != null) {
                setRecipientsCCO(recipientsCCO);
            }
            if (subject == null) {
                throw new RuntimeException("You should set the email subject by using setSubject(String subject)");
            } else {
                email.setSubject(subject);
            }
            if (message == null) {
                throw new RuntimeException("You should set the email message by using setMsg(String message)");
            } else {
                email.setMsg(message);
            }

            email.send();

            //Including recipients in the log...
            logger.info(new Date() + ": An email was sucesfully sent to ");
            for (InternetAddress nextRecp : recipients) {
                logger.info(nextRecp.getAddress() + ", ");
            }

        } catch (EmailException e) {
            logger.error(new Date() + ": Something went really wrong while trying to send an email. Stack trace is:");
            e.printStackTrace();
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSender(InternetAddress sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrom(InternetAddress sender) throws EmailException {
        if (sender.getPersonal() != null || !sender.getPersonal().equals("")) {
            email.setFrom(sender.getAddress(), sender.getPersonal());
        } else {
            email.setFrom(sender.getAddress());
        }
    }

    public void setRecipients(List<InternetAddress> recipients) throws EmailException {
        if (recipients != null && recipients.size() > 0) {
            this.recipients = recipients;
            for (InternetAddress nextRecipient : recipients) {
                setTo(nextRecipient);
            }
        } else {
            throw new RuntimeException("No recipient set for the email. At least, 1 should be set");
        }
    }

    public void setRecipientsCC(List<InternetAddress> recipientsCC) throws EmailException {
        if (recipientsCC != null && recipientsCC.size() > 0) {
            this.recipientsCC = recipientsCC;
            for (InternetAddress nextRecipient : recipientsCC) {
                setToCC(nextRecipient);
            }
        }
    }

    public void setRecipientsCCO(List<InternetAddress> recipientsCCO) throws EmailException {
        if (recipientsCCO != null && recipientsCCO.size() > 0) {
            this.recipientsCCO = recipientsCCO;
            for (InternetAddress nextRecipient : recipientsCCO) {
                setToCCO(nextRecipient);
            }
        }
    }

    private void setTo(InternetAddress recipient) throws EmailException {
        if (recipient.getPersonal() != null && !recipient.getPersonal().equals("")) {
            email.addTo(recipient.getAddress(), recipient.getPersonal());
        } else {
            email.addTo(recipient.getAddress());
        }
    }

    private void setToCC(InternetAddress recipient) throws EmailException {
        if (recipient.getPersonal() != null && !recipient.getPersonal().equals("")) {
            email.addCc(recipient.getAddress(), recipient.getPersonal());
        } else {
            email.addCc(recipient.getAddress());
        }
    }

    private void setToCCO(InternetAddress recipient) throws EmailException {
        if (recipient.getPersonal() != null && !recipient.getPersonal().equals("")) {
            email.addBcc(recipient.getAddress(), recipient.getPersonal());
        } else {
            email.addBcc(recipient.getAddress());
        }
    }
}
