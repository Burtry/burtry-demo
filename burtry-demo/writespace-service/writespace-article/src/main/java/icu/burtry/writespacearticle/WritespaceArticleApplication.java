package icu.burtry.writespacearticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "icu.burtry.apis")
public class WritespaceArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WritespaceArticleApplication.class, args);
	}

}
