package com.hanyu.project.mapper;

import com.hanyu.project.pojo.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    public Boolean insertOrder(OrderDO order);
}
