package com.seckillSystem.service;


import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.ItemDO;

public interface ItemService {
    public ItemModel getItemById(Integer id);

    public ItemDO getItemDO(Integer id);

}
