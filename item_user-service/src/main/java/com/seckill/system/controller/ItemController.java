package com.seckill.system.controller;

import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.ItemDO;
import com.seckillSystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/getItem")
    @ResponseBody
    public ItemModel getItemModel(Integer itemId) {
        ItemModel item = itemService.getItemByIdFromCache(itemId);
        if(item == null)
            throw new BusinessException(CommonError.ITEM_NOT_EXISTS_ERROR);
        return item;
    }

}
