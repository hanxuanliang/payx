package com.hxl.payx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxl.payx.PayxApplicationTests;
import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.service.impl.CartServiceImpl;
import com.hxl.payx.vo.CartVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/1/30 20:34
 */
@Slf4j
public class CartServiceImplTest extends PayxApplicationTests {

    @Autowired
    private CartServiceImpl cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void addtoCart() {
        CartAddForm cartAddForm = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm1 = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm2 = CartAddForm.builder().productId(27).build();
        cartService.addtoCart(101, cartAddForm);
        cartService.addtoCart(101, cartAddForm1);
        cartService.addtoCart(101, cartAddForm2);
    }

    @Test
    public void getForProductIds() {
        ResponseVo<CartVo> result = cartService.list(101);
        log.info("result: {}", gson.toJson(result));
    }
}