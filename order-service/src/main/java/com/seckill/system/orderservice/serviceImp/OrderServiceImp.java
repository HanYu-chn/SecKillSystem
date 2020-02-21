package com.seckill.system.orderservice.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.seckillSystem.orderService.OrderService;

@Service
@DefaultProperties(groupKey = "orderService",
        threadPoolKey = "orderService",
        threadPoolProperties = {
                @HystrixProperty(name="coreSize",value="5"),
                @HystrixProperty(name="maxQueueSize",value="5")},
        commandProperties={
                @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
        })
public class OrderServiceImp implements OrderService {
    @HystrixCommand(commandKey = "hello",
    groupKey = "orderService",
    threadPoolKey = "orderService"
    )
    @Override
    public String hello(String s) {
        return "Hello" + s;
    }
}
