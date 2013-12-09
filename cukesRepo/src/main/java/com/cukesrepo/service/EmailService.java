package com.cukesrepo.service;

import org.springframework.stereotype.Service;

import java.util.Properties;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Created by maduraisamy on 12/5/13.
 */
@Service
public class EmailService {


    final String username = "cukes.repo@gmail.com";
    final String password = "hackathon";

    public String send() {

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
                    InternetAddress.parse("mahesh.karthikd@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Kushang,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");
            return "success";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}




