package com.seckillSystem.controller;

import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value="/createOrder",method = RequestMethod.GET)
    @ResponseBody
    public ReturnResult createOrder(Integer userId, Integer itemId, Integer amount,
                                    Integer promoId, String token) throws BusinessException {
        //从redis中读取流量大闸token,判断token是否正确
//        StringBuilder builder = new StringBuilder();
//        builder.append("token_userId_").append(userId).append("_itemId_")
//                .append(itemId).append("_promoId_").append(promoId);
//        String tokenFromRedis =(String)redisTemplate.opsForValue().get(builder.toString());
//        if(tokenFromRedis == null || !tokenFromRedis.equals(token))
//            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"token错误");

        boolean result = orderService.checkAndCreateOrder(userId, itemId, amount, promoId);
        if(result == false) {
            throw new BusinessException(CommonError.UNKOWN_ERROR,"下单失败");
        } else {
            return ReturnResult.creat("下单成功");
        }
    }

}
