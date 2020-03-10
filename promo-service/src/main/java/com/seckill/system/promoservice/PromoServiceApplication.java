package com.seckill.system.promoservice;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;

@EnableDubboConfiguration
@MapperScan("com.seckill.system.promoservice.mapper")
@EnableHystrix
@SpringBootApplication
public class PromoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromoServiceApplication.class, args);
    }

}
