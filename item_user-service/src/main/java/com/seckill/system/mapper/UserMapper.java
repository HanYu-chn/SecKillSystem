package com.seckill.system.mapper;

import com.seckillSystem.pojo.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public UserDO getUserByTelephone(String telephone);
    public int checkUser(@Param("userName") String userName, @Param("passwordMD5") String passwordMD5);
    public boolean insertUser(UserDO userDO);
}
