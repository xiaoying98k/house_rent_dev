package com.yt.houserent.userprovider8001;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableDistributedTransaction
public class UserProvider8001Application {

    public static void main(String[] args) {
        SpringApplication.run(UserProvider8001Application.class, args);
    }

}
