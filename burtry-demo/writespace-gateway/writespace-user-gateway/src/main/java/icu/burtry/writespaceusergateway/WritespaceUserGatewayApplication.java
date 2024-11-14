package icu.burtry.writespaceusergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WritespaceUserGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WritespaceUserGatewayApplication.class, args);
    }

}
