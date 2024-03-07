package com.you;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统模块启动类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-11 16:36
 */
@EnableFeignClients(basePackages = {"com.you.*.api"})
@SpringBootApplication()
@MapperScan(basePackages = {"com.you.*.mapper"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
