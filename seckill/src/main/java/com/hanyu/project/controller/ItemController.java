package com.hanyu.project.controller;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.model.ItemModel;
import com.hanyu.project.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping("/getItemModel")
    @ResponseBody
    public ItemModel getItemModel(Integer itemId) {
        ItemModel item = itemService.getItemById(itemId);
        if(item == null)
            throw new BusinessException(CommonError.ITEM_NOT_EXISTS_ERROR);
        return item;
    }
}
