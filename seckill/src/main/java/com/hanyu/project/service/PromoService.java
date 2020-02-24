package com.hanyu.project.service;

import com.hanyu.project.pojo.PromoDO;

public interface PromoService {
    public PromoDO getPromoByItemId(Integer itemId);
    public PromoDO getPromoById(Integer id);
}
