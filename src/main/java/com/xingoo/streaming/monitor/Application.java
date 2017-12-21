package com.xingoo.streaming.monitor;

import com.xingoo.streaming.monitor.conf.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot 应用入口
 */
@SpringBootApplication
@Import(AppConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}