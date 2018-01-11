package com.xingoo.teddy;

import com.xingoo.teddy.config.AppConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot 应用入口
 */
@SpringBootApplication
@Import(AppConfig.class)
@MapperScan("com.xingoo.teddy.mapper")
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}