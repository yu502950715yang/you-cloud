package com.you;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * elasticsearch模块启动类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2024-07-02 16:36
 */
@EnableFeignClients(basePackages = {"com.you.*.api"})
@SpringBootApplication()
@EsMapperScan("com.you.es.mapper")
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
