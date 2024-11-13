package com.example.writespaceadmingateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WritespaceAdminGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WritespaceAdminGatewayApplication.class, args);
	}

}
