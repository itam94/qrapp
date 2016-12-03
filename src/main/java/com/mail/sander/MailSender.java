package com.mail.sander;

import com.sun.mail.smtp.SMTPMessage;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	 public MailSender(String mail, String login)throws MessagingException, AddressException, IOException{
	        Session session = buildGoogleSesion();
	        Message messageWithAttachment = buildMessageWithAttachment(session, login);
	        sendMessageToAddress(messageWithAttachment,mail);      
	        
	    }
	    
	    private static Session buildGoogleSesion()  {
	        Properties mailProps = new Properties();
	        mailProps.put("mail.transport.protocol", "smtp");
	        mailProps.put("mail.host", "smtp.gmail.com");
	        mailProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	        mailProps.put("mail.from", "mailsiedlce@gmail.com");
	        mailProps.put("mail.smtp.starttls.enable", "true");
	        mailProps.put("mail.smtp.port", "587");
	        mailProps.put("mail.smtp.auth", "true");
	        
	        final PasswordAuthentication usernamePassword = new PasswordAuthentication("mailsiedlce@gmail.com","hakunamatata12");
	        Authenticator auth = new Authenticator(){
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication(){
	                return usernamePassword;
	            }//dsfa
	        };
	        
	        Session session = Session.getInstance(mailProps, auth);
	        session.setDebug(true);
	        return session;        
	    }

	    
	    public static void sendMessageToAddress(Message simpleMessage, String mailto) 
	        throws MessagingException{
	        InternetAddress [] recipients = { new InternetAddress(mailto)};
	        Transport.send(simpleMessage, recipients);
	    }

	    public static Message buildMessageWithAttachment(Session session, String login) 
	            throws MessagingException, IOException{
	        SMTPMessage m = new SMTPMessage(session);
	        MimeMultipart content = new MimeMultipart();
	        
	        MimeBodyPart mainPart = new MimeBodyPart();
	        mainPart.setText("Czesc. to wiadomosc z zalacznikiem");
	        content.addBodyPart(mainPart);
	        
	        MimeBodyPart imagePart = new MimeBodyPart();
	        imagePart.attachFile("/tmp/zdjecie.png");
	        content.addBodyPart(imagePart);
	        
	        m.setContent(content);
	        m.setSubject("Wiadomość próbna ze zdjeciem");
	        return m;
	        

	    }

	   

	    public static void addressAndSendMessage(Message message, String recipient) 
	    throws AddressException, MessagingException {
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	        Transport.send(message);

	    }
}
