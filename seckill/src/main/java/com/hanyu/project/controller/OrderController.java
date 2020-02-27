package com.hanyu.project.controller;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.model.OrderModel;
import com.hanyu.project.mq.MQProducer;
import com.hanyu.project.response.ReturnResult;
import com.hanyu.project.service.OrderService;
import com.hanyu.project.service.StockWaterService;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    StockWaterService stockWaterService;

    @Autowired
    MQProducer producer;

    @RequestMapping("/createOrder")
    @ResponseBody
    public ReturnResult createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) throws BusinessException {
        //通过前端传入秒杀活动id,判断该活动是否对应商品且是否处于开始状态
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
