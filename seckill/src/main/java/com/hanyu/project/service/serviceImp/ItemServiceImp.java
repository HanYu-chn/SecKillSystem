package com.hanyu.project.service.serviceImp;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.mapper.ItemMapper;
import com.hanyu.project.model.ItemModel;
import com.hanyu.project.pojo.ItemDO;
import com.hanyu.project.pojo.PromoDO;
import com.hanyu.project.pojo.StockDO;
import com.hanyu.project.service.ItemService;
import com.hanyu.project.service.PromoService;
import com.hanyu.project.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImp implements ItemService{
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    StockService stockService;
    @Autowired
    PromoService promoService;
    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO item = itemMapper.getItemById(id);
        if(item == null) return null;
        StockDO stockDO = stockService.getStockByItemId(id);
        if(stockDO == null)
            throw new BusinessException(CommonError.STOCK_INFO_ERROR);
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setStock(stockDO.getStock());
        itemModel.setSales(stockDO.getSales());
        PromoDO promoDO = promoService.getPromoByItemId(id);
        itemModel.setPromoDO(promoDO);
        return itemModel;
    }

}
