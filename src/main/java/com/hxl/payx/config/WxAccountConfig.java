package com.hxl.payx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 微信支付账号 配置类
 * @Author: hanxuanliang
 * @Date: 2020/1/15 19:40
 */
@Configuration
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {

    private String appid;

    private String mchId;

    private String mchKey;

    private String notifyUrl;
}
