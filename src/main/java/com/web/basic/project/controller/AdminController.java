package com.web.basic.project.controller;

import com.web.basic.project.utils.ResultVoUtil;
import com.web.basic.project.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AdminController.java
 * @Description
 * @createTime 2020年03月13日 18:14:00
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping(value = "/getMessage")
    public ResultVo getMessage() {
        return ResultVoUtil.success("您拥有管理员权限，可以获得该接口的信息！");
    }
}
