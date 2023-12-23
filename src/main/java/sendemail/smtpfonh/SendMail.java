/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sendemail.smtpfonh;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author aleksandar
 */



public class SendMail {
    
    
    static private String from = "yourmail@mail.com";
    static private String password;
    static private String host = "smtp.gmail.com";
    private Properties prop;

    public SendMail(String password) {
        this.password = password;
        prop = System.getProperties();
        configureSystemProperties();
    }

    private void configureSystemProperties() {
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");
    }
    
    public Session getSessionObj(){
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("aleksandar.joksimovic@fonis.rs", password);

            }

        });
        session.setDebug(true);
        return session;
    }
    
    public void sendMail(String to,String subject, String message) throws Exception{
        
            Session session = getSessionObj();
            MimeMessage mimeMessage = new MimeMessage(session);

            // Set From: header field of the header.
            mimeMessage.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            mimeMessage.setSubject(subject);

            // Now set the actual message
            mimeMessage.setText(message);

            System.out.println("sending...");
            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
    
    }
    
    
    
    
    
    
    
    
}
