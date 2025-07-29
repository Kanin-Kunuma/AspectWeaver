package com.zcom.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zcom.aop.config.EnableAop; // 以你的包名为准

@SpringBootApplication
@EnableAop
public class DemoAopApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoAopApp.class, args);
    }
}