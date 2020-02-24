package com.hanyu.project.mapper;

import com.hanyu.project.pojo.PromoDO;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoMapper {
    public PromoDO getPromoById(int id);
    public PromoDO getPromoByItemId(int id);

}
