package com.hxl.payx.service;

import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.vo.CartVo;
import com.hxl.payx.vo.ResponseVo;

/**
 * @Description: 购物车 service
 * @Author: hanxuanliang
 * @Date: 2020/1/30 18:23
 */
public interface ICartService {

    ResponseVo<CartVo> addtoCart(Integer cartUid, CartAddForm cartAddForm);
}
