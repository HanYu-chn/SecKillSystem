package com.seckill.system.service.serviceImp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.mapper.ItemMapper;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.ItemModel;
import com.seckillSystem.pojo.ItemDO;
import com.seckillSystem.pojo.PromoDO;
import com.seckillSystem.service.ItemService;
import com.seckillSystem.service.PromoService;
import com.seckillSystem.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = ItemService.class)
public class ItemServiceImp implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    StockService stockService;

    @Reference
    PromoService promoService;

    @Autowired
    RedisTemplate redisTemplate;

    //直接从数据库获取数据
    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO item = itemMapper.getItemById(id);
        if(item == null) return null;
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        PromoDO promoDO = promoService.getPromoByItemId(id);
        itemModel.setPromoDO(promoDO);
        return itemModel;
    }

    //首先从本地缓存中取
    //若没有,则从redis取
    //最后再从数据库取
    @Override
    public ItemModel getItemByIdFromCache(Integer id) {
        ItemModel itemModel = (ItemModel)redisTemplate.opsForValue().get("info_item_" + id);
        if(itemModel == null) {
            itemModel = getItemById(id);
            if(itemModel == null)
                throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"无法获取商品信息");
            redisTemplate.opsForValue().set("info_item_" + id,itemModel);
            //redisTemplate.expire("info_item_" + id,5, TimeUnit.MINUTES);
        }
        return itemModel;
    }
}
