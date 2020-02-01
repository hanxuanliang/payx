package com.hxl.payx.service.impl;

import com.google.gson.Gson;
import com.hxl.payx.constants.MallConst;
import com.hxl.payx.constants.ProductStatusEnum;
import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.dao.ProductMapper;
import com.hxl.payx.entity.Cart;
import com.hxl.payx.entity.Product;
import com.hxl.payx.form.CartAddForm;
import com.hxl.payx.form.CartUpdateForm;
import com.hxl.payx.service.ICartService;
import com.hxl.payx.vo.CartProductVo;
import com.hxl.payx.vo.CartVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        return list(cartUid);
    }

    @Override
    public ResponseVo<CartVo> updateCart(Integer cartUid, Integer productId, CartUpdateForm cartUpdateForm) {
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);

        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        String cartData = opsHash.get(redisKey, String.valueOf(productId));
        // 没有该商品，return相应的错误即可
        if (!StringUtils.isEmpty(cartData)) {
            Cart cart = gson.fromJson(cartData, Cart.class);
            // 如果有该商品，则看修改条件，按需修改购物车中的商品数据
            if (cartUpdateForm.getQuatity() != null && cartUpdateForm.getQuatity() >= 0) {
                cart.setQuantity(cartUpdateForm.getQuatity());
            }
            if (cartUpdateForm.getSelected() != null) {
                cart.setProductSelected(cartUpdateForm.getSelected());
            }
            opsHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
            return list(cartUid);
        }
        return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
    }

    @Override
    public ResponseVo<CartVo> deleteFromCart(Integer cartUid, Integer productId) {

        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);

        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        String cartData = opsHash.get(redisKey, String.valueOf(productId));
        // 没有该商品，return相应的错误即可
        if (!StringUtils.isEmpty(cartData)) {
            opsHash.delete(redisKey, String.valueOf(productId));
            return list(cartUid);
        }
        return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer cartUid) {
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);
        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        for (Cart cart : getForCart(cartUid)) {
            cart.setProductSelected(true);
            opsHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(cartUid);
    }

    @Override
    public ResponseVo<CartVo> selectNone(Integer cartUid) {
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);
        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        for (Cart cart : getForCart(cartUid)) {
            cart.setProductSelected(false);
            opsHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(cartUid);
    }

    @Override
    public ResponseVo<Integer> sumInCartProducts(Integer cartUid) {
        Integer cartProductsSum = getForCart(cartUid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(cartProductsSum);
    }

    private ResponseVo<CartVo> list(Integer cartUid) {
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);
        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();

        List<Integer> productIds = getcartForIds(cartUid);
        List<Product> productsInCart = productMapper.findByProductIds(productIds);
        Map<String, String> entries = opsHash.entries(redisKey);

        CartVo cartVo = buildCartVo(productsInCart, entries);

        return ResponseVo.success(cartVo);
    }

    /**
     * 构造接口返回的CartVo
     * @param productsInCart 查询Productid范围的mapper返回的购物车中的Product集合
     * @param entries 查询rediskey返回的hash entries
     * @return 返回构造好的CartVo
     * @date: 2020/2/1 14:11
     */
    private CartVo buildCartVo(List<Product> productsInCart, Map<String, String> entries) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        CartProductVo cartProductVo;
        boolean selectAll = true;   // 是否全选
        BigDecimal cartTotalPrice = BigDecimal.ZERO;  // 总价格
        Integer cartTotalQuantity = 0;    // 购物车商品总数

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cartData = gson.fromJson(entry.getValue(), Cart.class);

            for (Product product : productsInCart) {
                if (product.getId().equals(productId)) {
                    cartProductVo = new CartProductVo(productId,
                            cartData.getQuantity(),
                            product.getName(),
                            product.getSubtitle(),
                            product.getMainImage(),
                            product.getPrice(),
                            product.getStatus(),
                            product.getPrice().multiply(BigDecimal.valueOf(cartData.getQuantity())),
                            product.getStock(),
                            cartData.getProductSelected()
                    );
                    cartProductVoList.add(cartProductVo);
                    if(!cartData.getProductSelected()){
                        selectAll = false;
                    }
                    if(cartData.getProductSelected()){
                        cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                    }
                }
                cartTotalQuantity += cartData.getQuantity();
            }
            cartVo.setCartProductVoList(cartProductVoList);
            cartVo.setSelectedAll(selectAll);
            cartVo.setCartTotalQuantity(cartTotalQuantity);
            cartVo.setCartTotalPrice(cartTotalPrice);
        }
        return cartVo;
    }

    /**
     * 查询redis中的购物车id，得到的Productids集合
     * @param cartUid redis中购物车id
     * @return Productids集合
     * @date: 2020/2/1 14:16
     */
    private List<Integer> getcartForIds(Integer cartUid) {
        List<Integer> cartForIds = new ArrayList<>();
        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);

        Map<String, String> entries = opsHash.entries(redisKey);

        entries.forEach((key, cartData) -> cartForIds.add(Integer.valueOf(key)));
        return cartForIds;
    }

    /**
     * 获取redis中存储的购物车中的存储的商品数据
     * @param cartUid redis中的购物车的Uid
     * @return List<Cart> cart数据集合
     * @date: 2020/2/1 16:57
     */
    private List<Cart> getForCart(Integer cartUid) {
        List<Cart> cartList = new ArrayList<>();
        HashOperations<String, String, String> opsHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(MallConst.REDIS_CART_TEMPLATE_KEY, cartUid);

        Map<String, String> entries = opsHash.entries(redisKey);

        entries.forEach((key, cartData) -> cartList.add(gson.fromJson(cartData, Cart.class)));
        return cartList;
    }
}
