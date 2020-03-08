package com.seckillSystem.service;

public interface PublishService {
    //发布商品信息到redis中
    public String publishItemInfo(Integer itemId);

    //发布促销信息到redis中
    public String publishPromoInfo(Integer promoId);

    //更新数据库商品库存到redis缓存中
    public boolean updateStock(Integer itemId);

    //更新数据库商品信息到redis缓存中
    public boolean updateItemInfo(Integer itemId);

    //更新数据库促销信息到redis缓存中
    public boolean updatePromoInfo(Integer promoId);

}
