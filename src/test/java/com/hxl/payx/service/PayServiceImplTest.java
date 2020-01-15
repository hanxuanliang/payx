package com.hxl.payx.service;

import com.hxl.payx.PayxApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/1/15 14:38
 */
public class PayServiceImplTest extends PayxApplicationTests {

    @Autowired
    private IPayService iPayService;

    @Test
    public void create() {
        // 千万不要使用 new BigDecimal(0.01)
        iPayService.create("hxl-122", BigDecimal.valueOf(0.01));
    }
}