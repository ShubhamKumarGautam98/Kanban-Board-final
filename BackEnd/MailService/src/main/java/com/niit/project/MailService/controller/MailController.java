package com.niit.project.MailService.controller;

import com.niit.project.MailService.domain.EmailDetails;
import com.niit.project.MailService.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v4")
public class MailController {
        @Autowired
        private final MailService emailService;

        public MailController(MailService emailService) {
                this.emailService = emailService;
        }
        @PostMapping("/sendEmail")
        public ResponseEntity<String> sendMail(@RequestBody EmailDetails emailDetails) {
                        return emailService.sendEmail(emailDetails);
        }

}
