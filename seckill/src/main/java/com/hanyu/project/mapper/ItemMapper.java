package com.hanyu.project.mapper;

import com.hanyu.project.pojo.ItemDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMapper {
    public ItemDO getItemById(Integer id);
}
