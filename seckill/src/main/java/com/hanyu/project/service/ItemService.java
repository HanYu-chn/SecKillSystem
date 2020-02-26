package com.hanyu.project.service;

import com.hanyu.project.model.ItemModel;
import com.hanyu.project.pojo.ItemDO;

public interface ItemService {
    public ItemModel getItemById(Integer id);

    public ItemDO getItemDO(Integer id);

}
