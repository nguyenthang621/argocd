package com.isttmicroservice.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.isttmicroservice.userservice.dto.MessageDTO;



@FeignClient(name ="notification-service" , url="http://localhost:8084")
public interface NotificationService {
	
	  @PostMapping("/send-notification")
	   void sendNotification(@RequestBody MessageDTO messageDTO);

}
