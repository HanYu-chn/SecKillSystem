package com.seckill.system.promoservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/promo")
@Controller
public class PromoController {

    @Autowired
    private PromoService promoService;


    //生成流量大闸的token
    @RequestMapping(value = "/generateToken",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult generateToken(String userName, Integer itemId, Integer promoId,
                                      String loginToken) {
        String token = promoService.generateToken(userName, itemId, promoId,loginToken);
        return ReturnResult.creat(token);
    }

    //获取促销信息
    @RequestMapping(value = "/getPromoById")
    @ResponseBody
    public ReturnResult getPromoById(Integer promoId) {
        PromoDO promoDO = promoService.getPromoByIdFromCache(promoId);
        return ReturnResult.creat(promoDO);
    }
}
