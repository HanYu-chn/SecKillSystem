package com.seckill.system.promoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public void show() {
        Object a = redisTemplate.opsForValue().get("A");
    }
}