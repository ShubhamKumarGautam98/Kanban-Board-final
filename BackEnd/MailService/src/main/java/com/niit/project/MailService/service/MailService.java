package com.niit.project.MailService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.project.MailService.domain.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    private final ObjectMapper objectMapper;

    public MailService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> sendEmail(EmailDetails emailDetails) {
        try {
            // creating a simple mail message
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            //Setting up necessary details
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMsgBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            // Sending the mail
            javaMailSender.send(simpleMailMessage);
            return ResponseEntity.ok().body(objectMapper.writeValueAsString("Mail sent successfully"));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while Sending Mail");
        }
    }

}

