package com.cukesrepo.service.email;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Created by maduraisamy on 12/5/13.
 */
@Service
public class EmailService {


    final String username = "cukes.repo@gmail.com";
    final String password = "hackathon";


    public String send(String toEmailAddress,String emailSubject,String emailBody) {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cuckesrepo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAddress));
            message.setSubject(emailSubject);
            message.setText(emailBody);

            Transport.send(message);

return "Success";


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}




