package com.you.gateway.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 防Xss注入配置
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-10-09 17:34
 **/
@Getter
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.xss")
public class XssProperties {

    /**
     * 放行url
     */
    private List<String> excludeUrls = new ArrayList<>();

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
