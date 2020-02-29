package com.seckillSystem.service;

public interface StockWaterService {
    Integer insertStockWater(Integer itemId, Integer amount, Integer status);
    Boolean updateStockWater(Integer status, Integer id);
    Integer getStatus(Integer id);
}
