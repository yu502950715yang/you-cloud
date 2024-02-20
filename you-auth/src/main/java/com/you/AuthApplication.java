package com.you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证中心启动类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-06 11:19
 */
@EnableFeignClients(basePackages = {"com.you.*.api"})
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
