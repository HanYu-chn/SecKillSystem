package com.hanyu.project.controller;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.model.UserModel;
import com.hanyu.project.response.ReturnResult;
import com.hanyu.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ReturnResult getUserInfo(String telephone,String password) throws BusinessException {
        ReturnResult result = new ReturnResult();
        UserModel user = userService.getUserByTelephone(telephone);
        if(user == null) {
            throw new BusinessException(CommonError.USER_NOT_EXISTS_ERROR);
        }
        user.setEncryptPassword(null);
        result.setData(user);
        return result;
    }

}
