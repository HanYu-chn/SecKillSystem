package com.hanyu.project.service;

import com.hanyu.project.pojo.StockWaterDO;
import org.apache.ibatis.annotations.Param;

public interface StockWaterService {
    Integer insertStockWater(Integer itemId,Integer amount,Integer status);
    Boolean updateStockWater(Integer status, Integer id);
    Integer getStatus(Integer id);
}
