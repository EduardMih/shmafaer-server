package com.licenta.shmafaerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShmafaerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShmafaerServerApplication.class, args);
    }

}
