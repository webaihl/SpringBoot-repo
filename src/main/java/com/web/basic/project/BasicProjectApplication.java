package com.web.basic.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author
 * Date: Created in 2019-03-12 11:29
 * Utils: Intellij Idea
 * Description: 启动类
 */
@SpringBootApplication
@MapperScan("com.web.basic.project.mapper")
public class BasicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicProjectApplication.class, args);
    }

}
