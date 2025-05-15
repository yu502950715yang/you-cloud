package com.you.common.core.domain;

import lombok.Data;

/**
 * ip 地理地址
 */
@Data
public class IpLocation {

    /**
     * 国家
     */
    private String country;

    /**
     * 区域
     */
    private String area;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商
     */
    private String isp;
}
