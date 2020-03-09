package com.seckill.system.service.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.mapper.StockWaterMapper;
import com.seckillSystem.pojo.StockWaterDO;
import com.seckillSystem.service.StockWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = StockWaterService.class)
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
