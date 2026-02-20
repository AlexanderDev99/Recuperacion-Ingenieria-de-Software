package com.uce.metricservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Importante

@SpringBootApplication
@EnableFeignClients // ¡Añade esto!
public class MetricserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricserviceApplication.class, args);
    }
}