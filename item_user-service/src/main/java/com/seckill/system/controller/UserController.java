package com.seckill.system.controller;

import com.seckillSystem.error.BusinessException;
import com.seckillSystem.pojo.UserDO;
import com.seckillSystem.response.ReturnResult;
import com.seckillSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult login(String userName, String password) throws BusinessException {
        return userService.login(userName,password);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult register(UserDO userDO) throws BusinessException {
        return userService.register(userDO);
    }

}
