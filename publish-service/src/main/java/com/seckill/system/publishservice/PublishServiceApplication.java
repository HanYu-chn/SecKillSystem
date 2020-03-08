package com.seckill.system.publishservice;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDubboConfiguration
@SpringBootApplication
public class PublishServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishServiceApplication.class, args);
    }

}
