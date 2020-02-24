package com.hanyu.project.service;

import com.hanyu.project.pojo.StockDO;
import org.springframework.stereotype.Service;

public interface StockService {
    public boolean decreaseStock(Integer itemId, Integer num);
    public StockDO getStockByItemId(Integer itemId);
}
