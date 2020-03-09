package com.seckill.system.mapper;

import com.seckillSystem.pojo.ItemDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMapper {
    public ItemDO getItemById(Integer id);
}
