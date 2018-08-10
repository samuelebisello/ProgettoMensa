package com.sam.testsecurityrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TestSecurityRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSecurityRestApplication.class, args);
    }

}
