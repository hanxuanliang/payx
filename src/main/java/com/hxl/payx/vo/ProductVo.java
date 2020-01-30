package com.hxl.payx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description: 商品 vo
 * @Author: hanxuanliang
 * @Date: 2020/1/30 11:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
