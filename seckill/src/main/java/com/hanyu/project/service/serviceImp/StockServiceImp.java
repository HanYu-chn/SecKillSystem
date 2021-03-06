package com.hanyu.project.service.serviceImp;

import com.hanyu.project.mapper.StockMapper;
import com.hanyu.project.mq.MQProducer;
import com.hanyu.project.pojo.StockDO;
import com.hanyu.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImp implements StockService {
    @Autowired
    StockMapper stockMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MQProducer mqProducer;

    @Override
    public boolean decreaseStock(Integer itemId, Integer num) {
        //从redis中扣减库存
        Long result = redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, -num);
        if(result < 0) {
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, num);
            return false;
        }
        //发布消息到MQ,让consumer执行数据库扣减操作
//        boolean flag = mqProducer.sendMessage(itemId,num);
//        if(flag == false)
//            redisTemplate.opsForValue().increment("promo_item_stock" + itemId, num);
        return true;
        //Integer result = stockMapper.decreaseStock(itemId,num);
    }

    @Override
    public StockDO getStockByItemId(Integer itemId) {
        return stockMapper.getStockByItemId(itemId);
    }
}
