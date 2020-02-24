package com.hanyu.project.controller;

import com.hanyu.project.error.BusinessException;
import com.hanyu.project.error.BussinessError;
import com.hanyu.project.error.CommonError;
import com.hanyu.project.response.ReturnResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
public class ExceptionController {
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
    public ReturnResult handlerException(HttpServletRequest req, Exception ex) {
        ReturnResult result = new ReturnResult();
        Map<String,Object> map = new HashMap<>();
        result.setStatus("fail");
        if(ex instanceof BusinessException) {
            map.put("code",((BusinessException)ex).getCode());
            map.put("errMsg",((BusinessException)ex).getErrMsg());
        } else {
            map.put("code",CommonError.UNKOWN_ERROR.getCode());
            map.put("errMsg",CommonError.UNKOWN_ERROR.getErrMsg());
        }
        result.setData(map);
        return result;
    }
}
