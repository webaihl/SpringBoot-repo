package com.web.basic.project.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

/**
 * @author
 * Date: Created in 18/8/29 上午9:54
 * Utils: Intellij Idea
 * Description: swagger bootstrapUI配置类
 */

@Configuration
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerBootStrapUIConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
