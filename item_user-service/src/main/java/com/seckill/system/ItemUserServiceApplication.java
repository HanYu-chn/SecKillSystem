package com.seckill.system;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;

@EnableDubboConfiguration
@MapperScan("com.seckill.system.mapper")
@EnableHystrix
@SpringBootApplication
public class ItemUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemUserServiceApplication.class, args);
    }
}
