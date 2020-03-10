package com.seckill.system.publishservice.serviceImp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.pojo.StockDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import com.seckillSystem.service.PublishService;
import com.seckillSystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishServiceImp implements PublishService {
    @Reference
    ItemService itemService;

    @Reference
    PromoService promoService;

    @Reference
    StockService stockService;

    @Autowired
    RedisTemplate redisTemplate;

    //发布商品信息到redis
    @Override
    public String publishItemInfo(Integer itemId) {
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        redisTemplate.opsForValue().set("info_item_" + itemId,itemModel);
        Object itemInfo = redisTemplate.opsForValue().get("info_item_" + itemId);
        if(itemInfo == null)
            throw new BusinessException(CommonError.UNKOWN_ERROR,"商品信息发布失败");
        return "success";
    }

    //发布促销信息到redis
    @Override
    public String publishPromoInfo(Integer promoId) {
        PromoDO promoDO = promoService.getPromoById(promoId);
        if(promoDO == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"促销信息不存在");
        redisTemplate.opsForValue().set("info_promo_" + promoId,promoDO);
        Object promoInfo = redisTemplate.opsForValue().get("info_promo_" + promoId);
        if(promoInfo == null)
            throw new BusinessException(CommonError.UNKOWN_ERROR,"促销信息发布失败");
        return "success";
    }


    //更新数据库商品库存到redis缓存中
    @Override
    public String publishStock(Integer itemId) {
        StockDO stockDO = stockService.getStockByItemId(itemId);
        if(stockDO == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"商品信息不正确");
        redisTemplate.opsForValue().set("promo_item_stock_" + itemId,stockDO.getStock());
        Object itemStock = redisTemplate.opsForValue().get("promo_item_stock_" + itemId);
        if(itemStock == null)
            throw new BusinessException(CommonError.UNKOWN_ERROR,"商品库存信息发布失败");
        return "success";
    }

    //发布流量大闸到redis中
    @Override
    public String publishStockTokenDoor(Integer itemId, Integer promoId) {
        StockDO stockDO = stockService.getStockByItemId(itemId);
        if(stockDO == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"商品信息不正确");
        StringBuilder builder = new StringBuilder();
        builder.append("tokenDoor_promoId_").append(promoId).append("_itemId_").append(itemId);
        redisTemplate.opsForValue().set(builder.toString(),stockDO.getStock() * 5);
        Object stockDoor = redisTemplate.opsForValue().get(builder.toString());
        if(stockDoor == null)
            throw new BusinessException(CommonError.UNKOWN_ERROR,"流量大闸发布失败");
        return "success";
    }

    //更新数据库商品信息到redis缓存中
    @Override
    public boolean updateItemInfo(Integer itemId) {
        return false;
    }

    //更新数据库促销信息到redis缓存中
    @Override
    public boolean updatePromoInfo(Integer promoId) {
        return false;
    }
}
