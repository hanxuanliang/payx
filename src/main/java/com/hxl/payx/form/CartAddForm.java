package com.hxl.payx.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description: 购物车 添加商品 form
 * @Author: hanxuanliang
 * @Date: 2020/1/30 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;

}
