package com.hanyu.project.controller;

import com.hanyu.project.response.ReturnResult;
import com.hanyu.project.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PromoController {

    @Autowired
    private PromoService promoService;

    @RequestMapping("/generateToken")
    @ResponseBody
    public ReturnResult generateToken(Integer userId,Integer itemId, Integer promoId) {
        String token = promoService.generateToken(userId, itemId, promoId);
        ReturnResult result = new ReturnResult();
        result.setData(token);
        return result;
    }
}
