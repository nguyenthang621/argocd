package com.isttmicroservice.monitoradmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
public class MonitorAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorAdminApplication.class, args);
	}

}
