package com.example.asgardiabutler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AsgardiaButlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsgardiaButlerApplication.class, args);
    }

}
