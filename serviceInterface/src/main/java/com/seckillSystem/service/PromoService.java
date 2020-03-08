package com.seckillSystem.service;

import com.seckillSystem.pojo.ItemDO;
import com.seckillSystem.pojo.PromoDO;

public interface PromoService {
    public PromoDO getPromoByItemId(Integer itemId);
    public PromoDO getPromoById(Integer id);
    public String generateToken(Integer userId, Integer itemId, Integer promoId
            , String loginToken, String userName,Integer amount);

}
