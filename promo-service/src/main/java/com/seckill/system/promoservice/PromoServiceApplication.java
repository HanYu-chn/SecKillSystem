package com.seckill.system.promoservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com.seckill.system.promoservice.mapper")
@ImportResource(value = {"consumer.xml","provider.xml"})
@EnableDubbo
@EnableHystrix
@SpringBootApplication
public class PromoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromoServiceApplication.class, args);
    }

}
