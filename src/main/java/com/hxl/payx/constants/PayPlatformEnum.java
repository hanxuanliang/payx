package com.hxl.payx.constants;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

/**
 * @Description: 支付平台 枚举类
 * @Author: hanxuanliang
 * @Date: 2020/1/16 10:28
 */
@Getter
public enum PayPlatformEnum {
    /**
     * @Description: 支付宝
     * @date: 2020/1/16 10:30
     */
    ALIPAY(1),

    /**
     * @Description: 微信
     * @date: 2020/1/16 10:30
     */
    WX(2),
    ;

    Integer platformCode;

    PayPlatformEnum(Integer platformCode) {
        this.platformCode = platformCode;
    }

    public static PayPlatformEnum payType2platform(BestPayTypeEnum payTypeEnum) {
//        if (payTypeEnum.getPlatform().name().equals(PayPlatformEnum.ALIPAY.name())) {
//            return PayPlatformEnum.ALIPAY;
//        } else if (payTypeEnum.getPlatform().name().equals(PayPlatformEnum.WX.name())) {
//            return PayPlatformEnum.WX;
//        }
        for (PayPlatformEnum platformEnum : PayPlatformEnum.values()) {
            if (payTypeEnum.getPlatform().name().equals(platformEnum.name())) {
                return platformEnum;
            }
        }
        throw new RuntimeException("错误的支付平台：" + payTypeEnum.name());
    }

}
