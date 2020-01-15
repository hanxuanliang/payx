package com.hxl.payx.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: BestPay 配置类，将微信账号信息注入到BestPay的配置中
 * @Author: hanxuanliang
 * @Date: 2020/1/15 17:42
 */
@Configuration
public class BestPayConfig {

    private final WxAccountConfig wxAccountConfig;

    @Autowired
    public BestPayConfig(WxAccountConfig wxAccountConfig) {
        this.wxAccountConfig = wxAccountConfig;
    }

    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }

    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();

        wxPayConfig.setAppId(wxAccountConfig.getAppid());
        wxPayConfig.setMchId(wxAccountConfig.getMchId());
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
        return wxPayConfig;
    }
}
