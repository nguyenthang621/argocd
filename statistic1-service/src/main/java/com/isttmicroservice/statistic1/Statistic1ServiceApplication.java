package com.isttmicroservice.statistic1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Statistic1ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Statistic1ServiceApplication.class, args);
	}

}
