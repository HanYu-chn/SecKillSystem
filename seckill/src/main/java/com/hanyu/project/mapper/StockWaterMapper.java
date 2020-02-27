package com.hanyu.project.mapper;

import com.hanyu.project.pojo.StockWaterDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface StockWaterMapper {
    Boolean insertStockWater(StockWaterDO stockWaterDO);
    Boolean updateStockWater(@Param("status") Integer status, @Param("id")Integer id);
    Integer selectStatus(Integer id);
}
