package com.hxl.payx.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 商品状态 枚举类
 * @Author: hanxuanliang
 * @Date: 2020/1/30 15:58
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ON_SALE(1, "在售"),

    DOWN_SALE(2, "下架"),

    DELETE(3, "删除"),
    ;

    Integer code;

    String desc;
}
