package com.hxl.payx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxl.payx.PayxApplicationTests;
import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.form.CartUpdateForm;
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

    // 比较好的方式去格式化美化输出的json字符串
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void addtoCart() {
        CartAddForm cartAddForm = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm1 = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm2 = CartAddForm.builder().productId(27).build();
        ResponseVo<CartVo> cartVoResponseVo = cartService.addtoCart(101, cartAddForm);
        ResponseVo<CartVo> cartVoResponseVo1 = cartService.addtoCart(101, cartAddForm1);
        ResponseVo<CartVo> cartVoResponseVo2 = cartService.addtoCart(101, cartAddForm2);
    }

    @Test
    public void getForProductIds() {
//        ResponseVo<CartVo> result = cartService.list(101);
//        log.info("result: {}", gson.toJson(result));
    }

    @Test
    public void updateCart() {
        CartUpdateForm cartUpdateForm = CartUpdateForm.builder().quatity(4).selected(false).build();
        ResponseVo<CartVo> cartVoResponseVo = cartService.updateCart(101, 26, cartUpdateForm);
        log.info("cartVoResponseVo: {}", gson.toJson(cartVoResponseVo));
    }
}