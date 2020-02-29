package com.seckillSystem.service;

import com.seckillSystem.model.UserModel;
import com.seckillSystem.pojo.UserDO;
import com.seckillSystem.response.ReturnResult;

public interface UserService {
    public UserModel getUserByTelephone(String telephone);
    public ReturnResult login(String userName, String password);
    public ReturnResult register(UserDO userDO);
}
