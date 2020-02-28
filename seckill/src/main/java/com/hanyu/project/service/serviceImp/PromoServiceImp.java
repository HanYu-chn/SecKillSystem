package com.hanyu.project.service.serviceImp;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.mapper.PromoMapper;
import com.hanyu.project.model.ItemModel;
import com.hanyu.project.pojo.PromoDO;
import com.hanyu.project.service.ItemService;
import com.hanyu.project.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PromoServiceImp implements PromoService {
    @Autowired
    PromoMapper promoMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String generateToken(Integer userId, Integer itemId, Integer promoId) {
        //校验redis中是否还有token数量
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
