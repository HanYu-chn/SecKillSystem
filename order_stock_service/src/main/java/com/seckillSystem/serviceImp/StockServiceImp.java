package com.seckillSystem.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.seckillSystem.mapper.StockMapper;
import com.seckillSystem.mq.MQProducer;
import com.seckillSystem.pojo.StockDO;
import com.seckillSystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = StockService.class)
public class StockServiceImp implements StockService {
    @Autowired
    StockMapper stockMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean decreaseStockFromRedis(Integer itemId, Integer num) {
        //从redis中扣减库存
        Long result = redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, -num);
        if(result < 0) {
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, num);
            return false;
        }
        return true;
    }

    @Override
    public boolean decreaseStockFromDB(Integer itemId, Integer num) {
        return stockMapper.decreaseStock(itemId,num) == 1;
    }

    @Override
    public StockDO getStockByItemId(Integer itemId) {
        return stockMapper.getStockByItemId(itemId);
    }
}
