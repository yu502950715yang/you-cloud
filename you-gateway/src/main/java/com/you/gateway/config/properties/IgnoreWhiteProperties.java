package com.you.gateway.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token放行名单
 * <p>
 * RefreshScope 动态刷新，根据nacos配置动态变化
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-06 14:01
 */
@Getter
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "auth.ignore")
public class IgnoreWhiteProperties {

    /**
     * 放行url
     */
    private List<String> whiteList = new ArrayList<>();

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
