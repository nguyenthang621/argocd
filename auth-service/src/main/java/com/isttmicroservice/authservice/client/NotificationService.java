package com.isttmicroservice.authservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.isttmicroservice.authservice.dto.MessageDTO;
import com.isttmicroservice.authservice.dto.StatisticDTO;

@FeignClient(name = "notification-service", fallback = NotificationServiceImpl.class)
public interface NotificationService {
	@Async
	@PostMapping("/send-notification")
	public void sendNotification(@RequestBody MessageDTO messageDTO);



}
@Component
class NotificationServiceImpl implements NotificationService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void sendNotification(MessageDTO messageDTO) {
		// fallback
		logger.error("Statistic Service is very slow");
	}
}
