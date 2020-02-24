package com.hanyu.project.mapper;

import com.hanyu.project.pojo.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public UserDO getUserByTelephone(String telephone);
}
