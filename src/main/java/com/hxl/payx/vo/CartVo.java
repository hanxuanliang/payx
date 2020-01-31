package com.hxl.payx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 购物车 vo
 * @Author: hanxuanliang
 * @Date: 2020/1/30 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
