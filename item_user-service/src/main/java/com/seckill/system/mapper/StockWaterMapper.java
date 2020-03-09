package com.seckill.system.mapper;

import com.seckillSystem.pojo.StockWaterDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockWaterMapper {
    Boolean insertStockWater(StockWaterDO stockWaterDO);
    Boolean updateStockWater(@Param("status") Integer status, @Param("id") Integer id);
    Integer selectStatus(Integer id);
}
