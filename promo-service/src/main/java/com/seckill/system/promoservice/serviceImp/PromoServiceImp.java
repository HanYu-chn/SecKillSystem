package com.seckill.system.promoservice.serviceImp;


import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.promoservice.mapper.PromoMapper;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.service.CacheService;
import com.seckillSystem.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImp implements PromoService {

    @Autowired
    PromoMapper promoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CacheService cacheService;

    //生成流量大闸放行的token
    @Override
    public String generateToken(String userName, Integer itemId, Integer promoId,
                                String loginToken) {
        //校验用户是否登录
        String mark = (String)redisTemplate.opsForValue().get("login_token_userName_" + userName);
        if(mark == null || !mark.equals(loginToken))
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"请先登录");
        //校验redis中是否还有token数量,通过promoId,itemId组合判断商品和促销活动是否对应,库存是否足够
        StringBuilder builder = new StringBuilder();
        builder.append("tokenDoor_promoId_").append(promoId).append("_itemId_").append(itemId);
        Long num = redisTemplate.opsForValue().increment(builder.toString(), -1);
        if(num < 0) {
            redisTemplate.opsForValue().increment(builder.toString(), 1);
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"库存不足");
        }
        String token = UUID.randomUUID().toString().replace("-","");
        StringBuilder tokenBuilder = new StringBuilder();
        //将生成的流量大闸token放置到redis缓存中
        tokenBuilder.append("tokenDoor_userName_").append(userName).append("_itemId_")
                .append(itemId).append("_promoId_").append(promoId);
        redisTemplate.opsForValue().set(tokenBuilder.toString(),token);
        redisTemplate.expire(tokenBuilder.toString(),5, TimeUnit.MINUTES);
        return token;
    }

    //根据ItemId获取促销信息
    @Override
    public PromoDO getPromoByItemId(Integer itemId) {
        return promoMapper.getPromoByItemId(itemId);
    }

    //根据促销id获取促销信息
    @Override
    public PromoDO getPromoById(Integer promoId) {
        return promoMapper.getPromoById(promoId);
    }

    //根据商品Id获取促销信息
    //首先尝试从本地缓存中获取
    //再尝试从redis中获取
    //最后再去数据库读取
    @Override
    public PromoDO getPromoByIdFromCache(Integer id) {
        PromoDO promoDO = (PromoDO)cacheService.getValue("info_promo_" + id);
        if(promoDO != null)
            return promoDO;
        promoDO = (PromoDO)redisTemplate.opsForValue().get("info_promo_" + id);
        if(promoDO == null) {
            System.out.println("---------------------------------");
            promoDO = getPromoById(id);
            if(promoDO == null)
                throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"无法获取促销信息");
            redisTemplate.opsForValue().set("info_promo_" + id,promoDO);
            //redisTemplate.expire("info_promo_" + id,5, TimeUnit.MINUTES);
        }
        cacheService.setValue("info_promo_" + id,promoDO);
        return promoDO;
    }
}
