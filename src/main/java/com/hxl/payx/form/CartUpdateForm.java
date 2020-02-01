package com.hxl.payx.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 购物车商品修改 form
 * @Author: hanxuanliang
 * @Date: 2020/2/1 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartUpdateForm {

    private Integer quatity;

    @Builder.Default
    private Boolean selected = true;
}
