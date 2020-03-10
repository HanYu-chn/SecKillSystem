package com.seckillSystem.service;

import com.seckillSystem.pojo.StockDO;

public interface StockService {
    public boolean decreaseStockFromRedis(Integer itemId, Integer num);
    public boolean decreaseStockFromDB(Integer itemId, Integer num);
    public StockDO getStockByItemId(Integer itemId);
}
