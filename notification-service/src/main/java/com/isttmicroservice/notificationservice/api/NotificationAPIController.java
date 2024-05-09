package com.isttmicroservice.notificationservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.notificationservice.dto.MessageDTO;
import com.isttmicroservice.notificationservice.service.EmailService;

@RestController
public class NotificationAPIController {
	@Autowired
	EmailService emailService;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody MessageDTO messageDTO) {
	emailService.sendEmail(messageDTO);
    }

}
