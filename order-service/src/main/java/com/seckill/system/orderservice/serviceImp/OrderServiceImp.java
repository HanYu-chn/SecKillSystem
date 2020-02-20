package com.seckill.system.orderservice.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.seckillSystem.orderService.OrderService;

@Service
public class OrderServiceImp implements OrderService {
    @Override
    public String hello(String s) {
        return "Hello" + s;
    }
}
