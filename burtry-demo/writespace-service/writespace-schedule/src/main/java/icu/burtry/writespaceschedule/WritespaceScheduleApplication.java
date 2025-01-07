package icu.burtry.writespaceschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "icu.burtry.apis")
@EnableAsync
public class WritespaceScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WritespaceScheduleApplication.class, args);
	}

}
