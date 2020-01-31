package com.hxl.payx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description: 购物车中的商品 vo
 * @Author: hanxuanliang
 * @Date: 2020/1/30 17:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductVo {

    private Integer productId;

    // 购买数量
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    // 商品总价：productTotalPrice = quantity * productPrice
    private BigDecimal productTotalPrice;

    private Integer productStock;

    // 购物车中的商品是否被选中
    private Boolean productSelected;
}
