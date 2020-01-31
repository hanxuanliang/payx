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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
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

    private final Gson gson;

    @Autowired
    public CartServiceImpl(ProductMapper productMapper, StringRedisTemplate stringRedisTemplate, Gson gson) {
        this.productMapper = productMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.gson = gson;
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
        Cart cart;

        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        String cartData = opsHash.get(redisKey, String.valueOf(product.getId()));
        if (StringUtils.isEmpty(cartData)) {
            cart = Cart.builder().productId(product.getId()).productSelected(cartAddForm.getSelected()).build();
        } else {
            cart = gson.fromJson(cartData, Cart.class);
            cart.setQuantity(cart.getQuantity() + 1);
        }

        opsHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));
        
        return null;
    }
}
