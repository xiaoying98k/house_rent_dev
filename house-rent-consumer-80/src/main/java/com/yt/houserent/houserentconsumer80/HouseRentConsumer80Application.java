package com.yt.houserent.houserentconsumer80;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.api.houseRentService")
@EnableRedisHttpSession
@Slf4j
@EnableDistributedTransaction
public class HouseRentConsumer80Application {

    public static void main(String[] args) {
        SpringApplication.run(HouseRentConsumer80Application.class, args);
    }

}
