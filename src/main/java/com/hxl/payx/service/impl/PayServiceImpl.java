package com.hxl.payx.service.impl;

import com.hxl.payx.constants.PayPlatformEnum;
import com.hxl.payx.dao.PayInfoMapper;
import com.hxl.payx.entity.PayInfo;
import com.hxl.payx.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
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

    private final PayInfoMapper payInfoMapper;

    @Autowired
    public PayServiceImpl(BestPayService bestPayService, PayInfoMapper payInfoMapper) {
        this.bestPayService = bestPayService;
        this.payInfoMapper = payInfoMapper;
    }

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum payTypeEnum) {
        // 将订单写到数据库中
        PayInfo payInfo = new PayInfo(
                Long.parseLong(orderId),
                PayPlatformEnum.payType2platform(payTypeEnum).getPlatformCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("6805711-Hree");    // replaceKey: 448279, 8034816
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(payTypeEnum);

        PayResponse response = bestPayService.pay(payRequest);
        log.info("发起支付 payresponse={}", response);
        return response;
    }

    @Override
    public String asyncNotify(String notiyData) {
        // 1. 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notiyData);
        log.info("异步通知 notifyResponse={}", payResponse);

        // 2. 金额校验
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        if (payInfo == null) {
            throw new RuntimeException("orderNo查询为空，重大报警");
        }

        // 3. 修改订单支付状态
        // 当订单状态此时不为“已支付”
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            // 当查询数据库中订单金额与返回通知中的金额不一致时，重大报警；
            // 金额大小比较建议使用 BigDecimal
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                throw new RuntimeException("异步通知中的金额与数据库中的不一致，orderNo = " + payResponse.getOrderId());
            }
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        // TODO 支付系统发送MQ消息，mall系统接收MQ消息

        // 4. 通知支付端不要重复通知
        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            // 4-1. 通知微信不要重复通知：直接return下面的xml到微信发起端就可以通知微信不要多次通知商户系统
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            // 4-2. 通知支付宝不要重复通知：直接返回success这7个字符回去，支付宝就不会重复通知
            return "success";
        }
        throw new RuntimeException("异步通知到错误的支付平台");
    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }
}
