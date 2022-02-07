package com.utility.api;

import com.utility.common.dto.EmailRequestDTO;
import com.utility.service.impl.EmailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("email")
public class EmailController {

    private final EmailSendingService emailSendingService;

    @Autowired
    public EmailController(EmailSendingService emailSendingService) {
        this.emailSendingService = emailSendingService;
    }

    @PostMapping("send")
    public ResponseEntity<String> createEmailRequest(@RequestBody EmailRequestDTO emailRequest) {
        emailSendingService.processEmailRequest(emailRequest);
        return ResponseEntity.accepted().build();
    }

}
