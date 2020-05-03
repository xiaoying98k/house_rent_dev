package com.yt.houserent.houseprovider8002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HouseProvider8002Application {

    public static void main(String[] args) {
        SpringApplication.run(HouseProvider8002Application.class, args);
    }

}
