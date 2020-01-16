package com.hxl.payx.controller;

import com.hxl.payx.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 支付 Controller
 * @Author: hanxuanliang
 * @Date: 2020/1/15 15:17
 */
@Controller
@RequestMapping("/payment")
public class PayController {
    // 微信支付下单api：https://api.mch.weixin.qq.com/pay/unifiedorder

    private final IPayService iPayService;

    @Autowired
    public PayController(IPayService iPayService) {
        this.iPayService = iPayService;
    }

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType")BestPayTypeEnum payTypeEnum) {
        PayResponse payResponse = iPayService.create(orderId, amount, payTypeEnum);

        // 支付方式不同，渲染层返回的就不同。
        // WXPAY_NATIVE 使用的是response.codeUrl；ALIPAY_PC 使用的是response.body
        Map<String, String> map = new HashMap<>(16);
        if (payTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            map.put("codeUrl", payResponse.getCodeUrl());
            return new ModelAndView("createForWxNative", map);
        } else if(payTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            map.put("body", payResponse.getCodeUrl());
            return new ModelAndView("createForAlipayPC", map);
        }
        throw new RuntimeException("暂不支持的支付方式");
    }

    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return iPayService.asyncNotify(notifyData);
    }
}
