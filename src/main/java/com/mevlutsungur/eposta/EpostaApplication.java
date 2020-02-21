package com.mevlutsungur.eposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EpostaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpostaApplication.class, args);
    }

}
