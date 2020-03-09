package com.seckill.system.service.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.mapper.OrderMapper;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.OrderModel;
import com.seckillSystem.pojo.OrderDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.OrderService;
import com.seckillSystem.service.StockService;
import com.seckillSystem.service.StockWaterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImp implements OrderService {
    @Autowired
    ItemService itemService;
    @Autowired
    StockService stockService;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StockWaterService stockWaterService;

    @Override
    @Transactional
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount, Integer promoId, Integer orderWaterId){
//        //校验下单状态,用户是否合法,购买数量是否正确,是否有促销活动
//        ItemModel item = itemService.getItemById(itemId);
//        if(item == null)
//            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"商品不存在");
//        //校验促销信息
//        PromoDO promo = promoService.getPromoById(promoId);
//        if(promo == null || promo.getItemId() != itemId)
//            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"促销信息和商品不符");

        //落单减库存(只扣减redis中的数据)
        boolean result = stockService.decreaseStock(itemId, amount);
        if(result == false)
            throw new BusinessException(CommonError.STOCK_NOT_ENOUGH,"库存可能不足,下单失败");

        //到这里,则商品正确,促销信息正确,redis缓存中扣减库存成功,producer将消息发送出去
        //改成了事务型消息

        //创建订单
        OrderDO order = new OrderDO();
//        item.setPrice(promo.getPromoPrice());
//        order.setPromoId(promoId);
//        order.setId("123456");
//        order.setItemId(itemId);
//        order.setUserId(userId);
//        order.setItemPrice(item.getPrice());
//        order.setAmount(amount);
//        order.setTotalPrice(item.getPrice() * amount);
        //result = orderMapper.insertOrder(order);
        //所有逻辑执行完成,修改订单流水状态
        stockWaterService.updateStockWater(1,orderWaterId);
        return convertOrderModelFromDO(order);
    }
    private OrderModel convertOrderModelFromDO(OrderDO orderDO) {
        OrderModel model = new OrderModel();
        BeanUtils.copyProperties(orderDO,model);
        return model;
    }
}
