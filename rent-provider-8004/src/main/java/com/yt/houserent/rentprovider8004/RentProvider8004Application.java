package com.yt.houserent.rentprovider8004;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableDistributedTransaction
public class RentProvider8004Application {

    public static void main(String[] args) {
        SpringApplication.run(RentProvider8004Application.class, args);
    }

}
