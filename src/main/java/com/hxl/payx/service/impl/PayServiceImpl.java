package com.hxl.payx.service.impl;

import com.hxl.payx.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description: 支付 Service 实现类
 * @Author: hanxuanliang
 * @Date: 2020/1/15 11:32
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    public final BestPayService bestPayService;

    @Autowired
    public PayServiceImpl(BestPayService bestPayService) {
        this.bestPayService = bestPayService;
    }

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum payTypeEnum) {
        // TODO 将订单写到数据库中
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("6805711-Hree");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(payTypeEnum);

        PayResponse response = bestPayService.pay(payRequest);
        log.info("response={}", response);
        return response;
    }

    @Override
    public String asyncNotify(String notiyData) {
        // 1. 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notiyData);
        log.info("payResponse={}", payResponse);

        // TODO 2. 金额校验

        // TODO 3. 修改订单支付状态

        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            // 4. 通知微信不要重复通知：直接return下面的xml到微信发起端就可以通知微信不要多次通知商户系统
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            // 4. 通知支付宝不要重复通知：直接返回success这7个字符回去，支付宝就不会重复通知
            return "success";
        }
        throw new RuntimeException("异步通知到错误的支付平台");
    }
}
