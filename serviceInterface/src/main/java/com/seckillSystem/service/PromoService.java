package com.seckillSystem.service;

import com.seckillSystem.pojo.PromoDO;

public interface PromoService {

    public PromoDO getPromoByItemId(Integer itemId);

    public PromoDO getPromoById(Integer id);

    //从缓存中获取促销信息
    public PromoDO getPromoByIdFromCache(Integer id);

    //生成流量大闸的token
    public String generateToken(String userName,Integer itemId, Integer promoId
            , String loginToken);

}
