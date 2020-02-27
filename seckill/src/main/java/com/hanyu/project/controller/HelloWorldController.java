package com.hanyu.project.controller;

import com.hanyu.project.pojo.StockWaterDO;
import com.hanyu.project.service.StockWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }
}
