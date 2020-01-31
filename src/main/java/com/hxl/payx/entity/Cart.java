package com.hxl.payx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 面向redis存储 cart 非实体类
 * @Author: hanxuanliang
 * @Date: 2020/1/30 19:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private Integer productId;

    @Builder.Default
    private Integer quantity = 1;

    private Boolean productSelected;

}
