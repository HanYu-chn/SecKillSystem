package com.seckillSystem.service;

import com.seckillSystem.pojo.StockDO;

public interface StockService {
    public boolean decreaseStock(Integer itemId, Integer num);
    public StockDO getStockByItemId(Integer itemId);
}
