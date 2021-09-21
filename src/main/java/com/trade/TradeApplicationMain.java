package com.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.trade")
public class TradeApplicationMain {

    public static void main(String args[]) {
        SpringApplication.run(TradeApplicationMain.class, args);
    }

}
