package com.seckill.system.mapper;

import com.seckillSystem.pojo.PromoDO;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoMapper {
    public PromoDO getPromoById(int id);
    public PromoDO getPromoByItemId(int id);

}
