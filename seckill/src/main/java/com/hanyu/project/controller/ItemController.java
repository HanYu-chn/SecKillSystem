package com.hanyu.project.controller;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.model.ItemModel;
import com.hanyu.project.pojo.ItemDO;
import com.hanyu.project.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
        ItemModel item = itemService.getItemById(itemId);
        if(item == null)
            throw new BusinessException(CommonError.ITEM_NOT_EXISTS_ERROR);
        return item;
    }

    @RequestMapping("/demo/getItem")
    @ResponseBody
    public ItemDO getItemDO(Integer itemId) {

        System.out.println("hello world --------------------");

        //从本地缓存中取(待实现)
        //从redis中取
        ItemDO itemDO = (ItemDO)redisTemplate.opsForValue().get("item_" + itemId);
        if(itemDO == null) {
            itemDO = itemService.getItemDO(itemId);
            redisTemplate.opsForValue().set("item_" + itemId,itemDO,30, TimeUnit.MINUTES);
        }
        return itemDO;
    }

}
