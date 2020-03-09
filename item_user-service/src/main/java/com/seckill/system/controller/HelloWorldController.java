package com.seckill.system.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seckillSystem.pojo.ItemDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @Reference
    PromoService promoService;

    @RequestMapping("/hello")
    @ResponseBody
    public String show() {
        ItemDO hello = promoService.hello();
        return "hello world";

    }
}
