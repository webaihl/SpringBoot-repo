package com.web.basic.project.controller;

import com.web.basic.project.mapper.UserMapper;
import com.web.basic.project.utils.ResultVoUtil;
import com.web.basic.project.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName LoginController.java
 * @Description
 * @createTime 2020年03月13日 18:16:00
 */
@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public ResultVo notLogin() {
        return ResultVoUtil.success("您尚未登陆！");
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public ResultVo notRole() {
        return ResultVoUtil.success("您没有权限！");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultVo logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return ResultVoUtil.success("成功注销！");
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVo login(String username, String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        subject.login(token);
        //根据权限，指定返回数据
        String role = userMapper.getRole(username);
        if ("user".equals(role)) {
            return ResultVoUtil.success("欢迎登陆");
        }
        if ("admin".equals(role)) {
            return ResultVoUtil.success("欢迎来到管理员页面");
        }
        return ResultVoUtil.success("权限错误！");
    }
}

