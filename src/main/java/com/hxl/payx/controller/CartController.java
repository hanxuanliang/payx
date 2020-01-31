package com.hxl.payx.controller;

import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.vo.CartVo;
import com.hxl.payx.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description: 购物车 controller
 * @Author: hanxuanliang
 * @Date: 2020/1/30 17:43
 */
@RestController
public class CartController {

    @PostMapping("/cart")
    public ResponseVo<CartVo> addCart(@Valid @RequestBody CartAddForm cartAddForm) {
        return null;
    }
}
