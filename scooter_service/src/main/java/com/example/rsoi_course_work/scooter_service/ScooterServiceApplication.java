package com.example.rsoi_course_work.scooter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScooterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScooterServiceApplication.class, args);
    }
}
