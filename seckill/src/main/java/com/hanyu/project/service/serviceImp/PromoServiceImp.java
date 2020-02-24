package com.hanyu.project.service.serviceImp;

import com.hanyu.project.mapper.PromoMapper;
import com.hanyu.project.pojo.PromoDO;
import com.hanyu.project.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoServiceImp implements PromoService {
    @Autowired
    PromoMapper promoMapper;
    @Override
    public PromoDO getPromoByItemId(Integer itemId) {
        return promoMapper.getPromoByItemId(itemId);
    }

    @Override
    public PromoDO getPromoById(Integer id) {
        return promoMapper.getPromoById(id);
    }
}
