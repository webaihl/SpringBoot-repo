package com.web.basic.project.controller;

import com.web.basic.project.utils.ResultVoUtil;
import com.web.basic.project.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName GuestController.java
 * @Description
 * @createTime 2020年03月13日 18:09:00
 */

@RestController
@RequestMapping("/guest")
public class GuestController{

    @GetMapping(value = "/enter")
    public ResultVo login() {
        return ResultVoUtil.success("欢迎进入，您的身份是游客");
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public ResultVo submitLogin() {
        return ResultVoUtil.success("您拥有获得该接口的信息的权限！");
    }
}