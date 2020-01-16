package com.hxl.payx.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description: 支付 Service
 * @Author: hanxuanliang
 * @Date: 2020/1/15 11:29
 */
public interface IPayService {

    /**
     * 创建/发起支付
     * @param openId 微信id
     * @param amount 数量
     * @param payTypeEnum 支付方式
     * @return void
     * @date: 2020/1/15 11:30
     */
    PayResponse create(String openId, BigDecimal amount, BestPayTypeEnum payTypeEnum);

    /**
     * 异步通知处理。支付成功之后，支付端对商户系统发起的异步通知
     * @param notiyData 异步数据
     * @return String 严格返回具体字符串
     * @date: 2020/1/15 17:40
     */
    String asyncNotify(String notiyData);
}
