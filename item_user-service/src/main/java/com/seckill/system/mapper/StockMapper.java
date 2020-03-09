package com.seckill.system.mapper;

import com.seckillSystem.pojo.StockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {
    public Integer decreaseStock(@Param("itemId") int itemId, @Param("num") int num);

    public StockDO getStockByItemId(Integer itemId);
}
