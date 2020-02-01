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
import org.junit.After;
import org.junit.Before;
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

    private Integer cartUid = 101;

    private Integer productId = 26;

    @Before
    public void addtoCart() {
        CartAddForm cartAddForm = CartAddForm.builder().productId(26).build();
        CartAddForm cartAddForm1 = CartAddForm.builder().productId(29).build();
        ResponseVo<CartVo> cartVoResponseVo = cartService.addtoCart(cartUid, cartAddForm);
        ResponseVo<CartVo> cartVoResponseVo1 = cartService.addtoCart(cartUid, cartAddForm1);
    }

    @Test
    public void updateCart() {
        CartUpdateForm cartUpdateForm = CartUpdateForm.builder()
                .quatity(4).selected(false)
                .build();
        ResponseVo<CartVo> cartVoResponseVo = cartService.updateCart(cartUid, productId, cartUpdateForm);
        log.info("cartVoResponseVo: {}", gson.toJson(cartVoResponseVo));
    }

    @Test
    public void selectOps() {
//        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(101);
//        log.info("cartVoResponseVo: {}", gson.toJson(cartVoResponseVo));
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectNone(cartUid);
        log.info("cartVoResponseVo: {}", gson.toJson(cartVoResponseVo));
    }

    @Test
    public void sumInCartProducts() {
        ResponseVo<Integer> integerResponseVo = cartService.sumInCartProducts(cartUid);
        log.info("cartVoResponseVo: {}", gson.toJson(integerResponseVo));
    }

    @After
    public void deleteFromCart() {
        ResponseVo<CartVo> cartVoResponseVo = cartService.deleteFromCart(cartUid, productId);
        log.info("cartVoResponseVo: {}", gson.toJson(cartVoResponseVo));
    }
}