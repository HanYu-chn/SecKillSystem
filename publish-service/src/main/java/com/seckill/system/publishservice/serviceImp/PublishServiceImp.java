package com.seckill.system.publishservice.serviceImp;

import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import com.seckillSystem.service.PublishService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishServiceImp implements PublishService {
    @Reference
    ItemService itemService;

    @Reference
    PromoService promoService;

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
}
