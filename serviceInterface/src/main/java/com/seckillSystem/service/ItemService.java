package com.seckillSystem.service;


import com.seckillSystem.model.ItemModel;

public interface ItemService {

    //直接从数据库获取
    public ItemModel getItemById(Integer id);

    //先从缓存中获取
    public ItemModel getItemByIdFromCache(Integer id);


}
