package com.seckillSystem.mapper;

import com.seckillSystem.pojo.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    public Boolean insertOrder(OrderDO order);
}
