package com.seckillSystem.serviceImp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.seckillSystem.Util.SnowFlakeGenerate;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.mapper.OrderMapper;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.model.OrderModel;
import com.seckillSystem.mq.MQProducer;
import com.seckillSystem.pojo.OrderDO;
import com.seckillSystem.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImp implements OrderService {

    private static SnowFlakeGenerate idWorker = new SnowFlakeGenerate(0, 0);

    @Reference
    ItemService itemService;

    @Autowired
    StockService stockService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StockWaterService stockWaterService;

    @Autowired
    MQProducer producer;

    //检测库存数量是否足够,创建订单,异步扣减数据库库存
    @Override
    public boolean checkAndCreateOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) {
        //扣减redis缓存中的库存情况
        boolean result = stockService.decreaseStockFromRedis(itemId, amount);
        if(result == false)
            throw new BusinessException(CommonError.STOCK_NOT_ENOUGH,"库存可能不足,下单失败");

        //todo  若redis扣减成功,则直接返回订单正在处理中,剩下的步骤交给线程池处理.

        //若redis库存扣减成功,则创建库存流水,供事务消息检测本地事务的执行情况
        Integer orderWaterId = stockWaterService.insertStockWater(itemId, amount, 0);

        //异步发布数据库扣减库存消息
        result = producer.sendMessage(userId,itemId,amount,promoId,orderWaterId);

        if(result == false) {
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount);
            throw new BusinessException(CommonError.UNKOWN_ERROR, "下单失败");
        }
        return true;
    }

    //创建订单,并完成库存流水的更新,供事务型消息进行检测
    @Override
    @Transactional
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount, Integer promoId, Integer orderWaterId){
        //创建订单
        OrderDO order = new OrderDO();
        ItemModel itemModel = itemService.getItemByIdFromCache(itemId);
        order.setPromoId(promoId);
        order.setId(idWorker.nextId());
        order.setItemId(itemId);
        order.setUserId(userId);
        order.setItemPrice(itemModel.getPromoDO().getPromoPrice());
        order.setAmount(amount);
        order.setTotalPrice(order.getItemPrice() * amount);
        Boolean result = orderMapper.insertOrder(order);
        if(result == false) {
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount);
            throw new BusinessException(CommonError.UNKOWN_ERROR, "创建订单失败");
        }
        //所有逻辑执行完成,修改订单流水状态
        result = stockWaterService.updateStockWater(1, orderWaterId);
        if(result == false) {
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount);
            throw new BusinessException(CommonError.UNKOWN_ERROR,"库存流水更新失败");
        }
        return convertOrderModelFromDO(order);
    }

    private OrderModel convertOrderModelFromDO(OrderDO orderDO) {
        OrderModel model = new OrderModel();
        BeanUtils.copyProperties(orderDO,model);
        return model;
    }
}
