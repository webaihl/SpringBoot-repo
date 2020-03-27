package com.web.basic.project.config.shiro;

import com.web.basic.project.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CustomRealm.java
 * @Description
 * @createTime 2020年03月13日 17:39:00
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    private UserMapper userMapper;

    @Autowired
    private void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 获取**授权**信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入用户授权方法~~~");
        //获取当前登录的用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userMapper.getRole(username);
        Set<String> roles = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        roles.add(role);
        //设置该用户拥有的角色
        info.setRoles(roles);
        //设置拥有的权限列表
        //info.addStringPermissions(null);
        return info;
    }

    /**
     * 获取**身份验证**信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入身份认证方法~~~");
        //获取传入的token 里边包含了用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //从数据库获取密码
        String password = userMapper.getPassword(token.getUsername());
        if (null == password) {
            throw new AccountException("该用户不存在");
        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }
}
