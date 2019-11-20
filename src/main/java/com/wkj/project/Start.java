package com.wkj.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.wkj.project.mapper"})
@Configuration
@ComponentScan(basePackages = {"com.wkj.project"})
@EnableCaching
@ServletComponentScan
public class Start {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
        System.out.println("system started");
    }
}
