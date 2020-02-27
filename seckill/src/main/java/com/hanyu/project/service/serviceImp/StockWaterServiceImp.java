package com.hanyu.project.service.serviceImp;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.mapper.StockWaterMapper;
import com.hanyu.project.pojo.StockWaterDO;
import com.hanyu.project.service.StockWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockWaterServiceImp implements StockWaterService {
    @Autowired
    private StockWaterMapper stockWaterMapper;
    @Override
    public Integer insertStockWater(Integer itemId,Integer amount,Integer status) {
        StockWaterDO stockWaterDO = new StockWaterDO();
        stockWaterDO.setItemId(itemId);
        stockWaterDO.setStatus(status);
        stockWaterDO.setAmount(amount);
        return stockWaterMapper.insertStockWater(stockWaterDO) ? stockWaterDO.getId() : null;
    }

    @Override
    public Boolean updateStockWater(Integer status, Integer id) {
        return stockWaterMapper.updateStockWater(status,id);
    }

    @Override
    public Integer getStatus(Integer id) {
        return stockWaterMapper.selectStatus(id);
    }
}
