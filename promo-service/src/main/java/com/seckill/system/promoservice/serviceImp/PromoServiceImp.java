package com.seckill.system.promoservice.serviceImp;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.promoservice.mapper.PromoMapper;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Component
public class PromoServiceImp implements PromoService {
    @Autowired
    PromoMapper promoMapper;

    @Reference
    ItemService itemService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String generateToken(Integer userId, Integer itemId, Integer promoId,
                                String loginToken,String userName) {
        //校验用户是否登录
        String mark = (String)redisTemplate.opsForValue().get("login_token_userName_" + userName);
        if(mark == null || !mark.equals(loginToken))
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"请先登录");
//        校验redis中是否还有token数量
        Long num = redisTemplate.opsForValue().increment("tokenDoor_" + promoId, -1);
        if(num < 0) {
            return null;
        }
        //校验用户合法性,商品是否存在,促销活动与商品是否对应
        //校验下单状态,用户是否合法,购买数量是否正确,是否有促销活动
        ItemModel item = itemService.getItemById(itemId);
        if(item == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        //校验促销信息
        PromoDO promo = getPromoById(promoId);
        if(promo == null || promo.getItemId() != itemId)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"促销信息和商品不符");
        String token = UUID.randomUUID().toString().replace("-","");
        System.out.println(token);
        StringBuilder builder = new StringBuilder();
        builder.append("token_userId_").append(userId).append("_itemId_")
                .append(itemId).append("_promoId_").append(promoId);
        redisTemplate.opsForValue().set(builder.toString(),token);
        redisTemplate.expire(builder.toString(),5, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public PromoDO getPromoByItemId(Integer itemId) {
        return promoMapper.getPromoByItemId(itemId);
    }

    @Override
    public PromoDO getPromoById(Integer id) {
        return promoMapper.getPromoById(id);
    }
}
