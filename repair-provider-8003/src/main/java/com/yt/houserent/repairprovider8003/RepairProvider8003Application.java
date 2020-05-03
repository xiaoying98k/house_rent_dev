package com.yt.houserent.repairprovider8003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RepairProvider8003Application {

    public static void main(String[] args) {
        SpringApplication.run(RepairProvider8003Application.class, args);
    }

}
