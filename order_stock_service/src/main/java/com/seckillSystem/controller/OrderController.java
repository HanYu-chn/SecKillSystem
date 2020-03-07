package com.seckillSystem.controller;

import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.mq.MQProducer;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.OrderService;
import com.seckillSystem.service.StockWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    StockWaterService stockWaterService;

    @Autowired
    MQProducer producer;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/createOrder")
    @ResponseBody
    public ReturnResult createOrder(Integer userId, Integer itemId, Integer amount,
                                    Integer promoId, String token) throws BusinessException {
        //从redis中读取流量大闸token,判断token是否正确
        StringBuilder builder = new StringBuilder();
        builder.append("token_userId_").append(userId).append("_itemId_")
                .append(itemId).append("_promoId_").append(promoId);
        String tokenFromRedis =(String)redisTemplate.opsForValue().get(builder.toString());
        if(tokenFromRedis == null || !tokenFromRedis.equals(token))
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"token错误");

        ReturnResult result = new ReturnResult();
        //创建订单流水
        Integer orderWaterId = stockWaterService.insertStockWater(itemId, amount, 0);
        String msg = producer.sendMessage(userId,itemId,amount,promoId,orderWaterId) ?
                "下单成功" : "下单失败";
//        OrderModel orderModel = orderService.creatOrder(userId, itemId, amount,promoId);
//        if(orderModel == null) {
//            throw new BusinessException(CommonError.USER_NOT_EXISTS_ERROR);
//        }
//        result.setData(orderModel);
        result.setData(msg);
        return result;
    }

}
