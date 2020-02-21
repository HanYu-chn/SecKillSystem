package com.seckill.system.orderweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.seckillSystem.orderService.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class OrderController {
    @Reference
    OrderService orderService;

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/orderConfirm")
    public String confirm(String name){
        return orderService.hello(name);
    }

    public String fallback(String content) {
        return "fallback";
    }
}
