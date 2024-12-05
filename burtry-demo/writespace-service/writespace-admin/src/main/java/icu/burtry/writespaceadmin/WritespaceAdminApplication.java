package icu.burtry.writespaceadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "icu.burtry.apis")
public class WritespaceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WritespaceAdminApplication.class, args);
    }

}
