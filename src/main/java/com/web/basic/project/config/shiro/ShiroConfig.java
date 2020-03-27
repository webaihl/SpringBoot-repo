package com.web.basic.project.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ShiroConfig.java
 * @Description
 * @createTime 2020年03月13日 17:13:00
 */

/***
 *  1、Subject代表用户或者第三方系统
 *  2、SecurityManager 存放所有的Subject
 *  3、Realm 获取安全数据(用户、角色、权限)
 */
@Configuration
@Slf4j
public class ShiroConfig {
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shirFilter(SecurityManager  securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登录跳转的地址，默认 工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        //无权限时跳转的地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        //设置拦截器map
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public DefaultWebSecurityManager  securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm());
        //注入记住我管理器;
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
        //注入缓存管理，防止doGetAuthorizationInfo多次执行
        defaultWebSecurityManager.setCacheManager(cacheManager());
        return defaultWebSecurityManager;
    }

    /**
     * 自定义realm
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     * 将CustomRealm交予Spring管理，否则获取不到IOC容器中的其他类， 如UserMapper
     * @return
     */
    @Bean
    public CustomRealm  customRealm(){
        return new CustomRealm();
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie对象;
     * @return
     */
    @Bean
    public SimpleCookie simpleCookie(){
       log.info("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //记住我cookie生效时间 ,单位秒
        simpleCookie.setMaxAge(60 * 60 * 24 * 30);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * @return
     */
    @Bean("rememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("m0@5ZZ9L4jjQXn7MREp^b^7I"));
        return cookieRememberMeManager;
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
