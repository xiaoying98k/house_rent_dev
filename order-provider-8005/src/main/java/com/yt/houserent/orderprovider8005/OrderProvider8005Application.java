package com.yt.houserent.orderprovider8005;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderProvider8005Application {

    public static void main(String[] args) {
        SpringApplication.run(OrderProvider8005Application.class, args);
    }

}
