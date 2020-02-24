package com.hanyu.project.service;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.model.UserModel;

public interface UserService {
    public UserModel getUserByTelephone(String telephone) throws BusinessException;
}
