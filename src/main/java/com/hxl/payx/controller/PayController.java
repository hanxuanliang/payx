package com.hxl.payx.controller;

import com.hxl.payx.service.IPayService;
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
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("amount") BigDecimal amount) {
        PayResponse payResponse = iPayService.create(orderId, amount);
        Map<String, String> map = new HashMap<>(16);
        map.put("codeUrl", payResponse.getCodeUrl());
        return new ModelAndView("create", map);
    }

    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return iPayService.asyncNotify(notifyData);
    }
}
