package com.hanyu.project.service;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.model.OrderModel;

public interface OrderService {
    public OrderModel creatOrder(Integer userId,Integer itemId, Integer amount, Integer promoId,Integer orderWaterId);

}
