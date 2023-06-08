package com.you.system.api.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * Description on this file, you will change here.
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-25 09:35
 */
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
