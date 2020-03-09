package com.seckill.system.service.serviceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.seckill.system.mapper.UserMapper;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.model.UserModel;
import com.seckillSystem.pojo.UserDO;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ReturnResult login(String userName, String password) {
        String passwordMD5 = null;
        try {
            passwordMD5 = encryptPasswordMD5(password);
        } catch (Exception e) {
            throw new BusinessException(CommonError.UNKOWN_ERROR,"加密失败");
        }
        int result = userMapper.checkUser(userName,passwordMD5);
        if(result != 1)
            throw new BusinessException(CommonError.PARAMETER_VALIDATION_ERROR,"登录失败");
        String token = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("login_token_userName_" + userName,token);
        redisTemplate.expire("login_token_userName_" + userName,2, TimeUnit.DAYS);
        return ReturnResult.creat(token);
    }

    @Override
    public ReturnResult register(UserDO userDO) {
        String password = userDO.getEncryptPassword();
        String passwordMD5 = null;
        try {
            passwordMD5 = encryptPasswordMD5(password);
        } catch (Exception e) {
            throw new BusinessException(CommonError.UNKOWN_ERROR,"加密失败");
        }
        userDO.setEncryptPassword(passwordMD5);
        boolean result = userMapper.insertUser(userDO);
        return ReturnResult.creat(result? "注册成功" : "注册失败");
    }

    @Override
    public UserModel getUserByTelephone(String telephone) throws BusinessException{
        UserDO userDO = userMapper.getUserByTelephone(telephone);
        return convertUserModelFromDo(userDO);
    }

    private String encryptPasswordMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String passwordMD5 = base64en.encode(md5.digest(password.getBytes("utf-8")));
        return passwordMD5;
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
