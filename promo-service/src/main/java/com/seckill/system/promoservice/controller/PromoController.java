package com.seckill.system.promoservice.controller;

import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PromoController {

    @Autowired
    private PromoService promoService;

    @RequestMapping(value = "/generateToken",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult generateToken(Integer userId, Integer itemId, Integer promoId,
                                      String loginToken, String userName) {
        String token = promoService.generateToken(userId, itemId, promoId,loginToken,userName);
        ReturnResult result = new ReturnResult();
        result.setData(token);
        return result;
    }
}
