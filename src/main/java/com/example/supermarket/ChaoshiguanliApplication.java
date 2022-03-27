package com.example.supermarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.supermarket.mapper")
public class ChaoshiguanliApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaoshiguanliApplication.class, args);
    }

}
