package com.hxl.payx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 支付宝支付项 配置项
 * @Author: hanxuanliang
 * @Date: 2020/1/16 9:17
 */
@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayAccountConfig {

    private String appId;

    private String privateKey;

    private String publicKey;

    private String notifyUrl;

    private String returnUrl;
}
