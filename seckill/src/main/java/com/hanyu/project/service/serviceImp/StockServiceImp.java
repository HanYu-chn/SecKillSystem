package com.hanyu.project.service.serviceImp;

import com.hanyu.project.mapper.StockMapper;
import com.hanyu.project.pojo.StockDO;
import com.hanyu.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImp implements StockService {
    @Autowired
    StockMapper stockMapper;
    @Override
    public boolean decreaseStock(Integer itemId, Integer num) {
        Integer result = stockMapper.decreaseStock(itemId,num);
        return result == 1 ? true : false;
    }

    @Override
    public StockDO getStockByItemId(Integer itemId) {
        return stockMapper.getStockByItemId(itemId);
    }
}
