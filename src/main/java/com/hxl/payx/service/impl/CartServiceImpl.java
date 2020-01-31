package com.hxl.payx.service.impl;

import com.google.gson.Gson;
import com.hxl.payx.constants.MallConst;
import com.hxl.payx.constants.ProductStatusEnum;
import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.dao.ProductMapper;
import com.hxl.payx.entity.Cart;
import com.hxl.payx.entity.Product;
import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.service.ICartService;
import com.hxl.payx.vo.CartVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: 购物车 service 实现类
 * @Author: hanxuanliang
 * @Date: 2020/1/30 18:38
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    private final ProductMapper productMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    @Autowired
    public CartServiceImpl(ProductMapper productMapper, StringRedisTemplate stringRedisTemplate) {
        this.productMapper = productMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ResponseVo<CartVo> addtoCart(Integer cartUid, CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        // 1.商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        // 2.商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_DOWM_OR_DELETE);
        }
        // 3.库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }
        // 4.写入redis  key:cart_cartUid
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);
        Cart cart = new Cart(product.getId(), 1, cartAddForm.getSelected());
        log.info("redisKey: {}", redisKey);
        log.info("cart: {}", cart);
        stringRedisTemplate.opsForValue().set(redisKey, gson.toJson(cart));
        
        return null;
    }
}
