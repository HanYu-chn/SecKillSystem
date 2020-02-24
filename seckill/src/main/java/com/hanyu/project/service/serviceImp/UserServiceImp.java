package com.hanyu.project.service.serviceImp;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.mapper.UserMapper;
import com.hanyu.project.model.UserModel;
import com.hanyu.project.pojo.PasswordDO;
import com.hanyu.project.pojo.UserDO;
import com.hanyu.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserModel getUserByTelephone(String telephone) throws BusinessException{
        UserDO userDO = userMapper.getUserByTelephone(telephone);
        return convertUserModelFromDo(userDO);
    }


    private UserModel convertUserModelFromDo(UserDO userDO) throws BusinessException{
        if(userDO == null)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR);
        UserModel userModel = new UserModel();
        userModel.setId(userDO.getId());
        userModel.setName(userDO.getName());
        userModel.setTelephone(userDO.getTelephone());
        userModel.setEncryptPassword(userDO.getEncryptPassword());
        return userModel;
    }
}
