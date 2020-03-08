package com.seckill.system.publishservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    @Autowired
    PublishService publishService;

    @Reference
    ItemService itemService;
    @RequestMapping("/hello")
    public ReturnResult hello() {
        return ReturnResult.creat(itemService.hello());
    }

    @RequestMapping("/publishItemInfo")
    public ReturnResult publishItemInfo(Integer itemId) {
        return ReturnResult.creat(publishService.publishItemInfo(itemId));
    }

    @RequestMapping("/publishPromoInfo")
    public ReturnResult publishPromoInfo(Integer promoId) {
        return ReturnResult.creat(publishService.publishPromoInfo(promoId));
    }
}
