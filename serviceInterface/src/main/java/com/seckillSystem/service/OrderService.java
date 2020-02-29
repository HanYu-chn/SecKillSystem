package com.seckillSystem.service;

import com.seckillSystem.model.OrderModel;

public interface OrderService {
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount, Integer promoId, Integer orderWaterId);

}
