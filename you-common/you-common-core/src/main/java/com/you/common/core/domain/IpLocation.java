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

    /**
     * 获取完整地址
     *
     * @return 完整地址
     */
    public String getFullAddress() {
        String country = this.country == null ? "" : this.country.equals("0") ? "" : this.country;
        String area = this.area == null ? "" : this.area.equals("0") ? "" : this.area;
        String province = this.province == null ? "" : this.province.equals("0") ? "" : this.province;
        String city = this.city == null ? "" : this.city.equals("0") ? "" : this.city;
        return country + area + province + city;
    }
}
