package icu.burtry.writespaceuserbehavior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "icu.burtry.apis")
public class WritespaceUserBehaviorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WritespaceUserBehaviorApplication.class, args);
    }

}
