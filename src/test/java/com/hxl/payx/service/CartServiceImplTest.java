package com.hxl.payx.service;

import com.hxl.payx.PayxApplicationTests;
import com.hxl.payx.form.CartAddForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/1/30 20:34
 */
@Slf4j
public class CartServiceImplTest extends PayxApplicationTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void addtoCart() {
        CartAddForm cartAddForm = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm1 = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm2 = CartAddForm.builder().productId(27).build();
        cartService.addtoCart(101, cartAddForm);
        cartService.addtoCart(101, cartAddForm1);
        cartService.addtoCart(101, cartAddForm2);
    }
}