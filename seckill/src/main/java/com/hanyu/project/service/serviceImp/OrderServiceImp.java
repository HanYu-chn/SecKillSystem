package com.hanyu.project.service.serviceImp;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.mapper.OrderMapper;
import com.hanyu.project.model.ItemModel;
import com.hanyu.project.model.OrderModel;
import com.hanyu.project.pojo.*;
import com.hanyu.project.service.ItemService;
import com.hanyu.project.service.OrderService;
import com.hanyu.project.service.PromoService;
import com.hanyu.project.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    ItemService itemService;
    @Autowired
    StockService stockService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    PromoService promoService;
    @Override
    @Transactional
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount, Integer promoId){
        //校验下单状态,用户是否合法,购买数量是否正确,是否有促销活动
        ItemModel item = itemService.getItemById(itemId);
        if(item == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR);
        //落单减库存
        boolean result = stockService.decreaseStock(itemId, amount);
        if(result == false)
            throw new BusinessException(CommonError.STOCK_NOT_ENOUGH);
        //判断秒杀活动
        OrderDO order = new OrderDO();
        if(promoId != null) {
            PromoDO promo = promoService.getPromoById(promoId);
            if(promo != null && promo.getItemId() == itemId) {
                item.setPrice(promo.getPromoPrice());
                order.setPromoId(promoId);
            }
        }
        //订单入库
        order.setId("123456");
        order.setItemId(itemId);
        order.setUserId(userId);
        order.setItemPrice(item.getPrice());
        order.setAmount(amount);
        order.setTotalPrice(item.getPrice() * amount);
        result = orderMapper.insertOrder(order);
        if(item == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR);
        //返回前端
        return convertOrderModelFromDO(order);
    }
    private OrderModel convertOrderModelFromDO(OrderDO orderDO) {
        OrderModel model = new OrderModel();
        BeanUtils.copyProperties(orderDO,model);
        return model;
    }
}
