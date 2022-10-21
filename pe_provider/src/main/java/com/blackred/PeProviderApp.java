package com.blackred;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class PeProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(PeProviderApp.class,args);
    }
}
