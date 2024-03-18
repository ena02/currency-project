package com.ena.bank.currency;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CurrencyApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }





}
