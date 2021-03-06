package com.hxl.payx.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 响应码 枚举
 * @Author: hanxuanliang
 * @Date: 2020/1/29 11:30
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {
    /**
     * 响应状态枚举，包括错误码，错误信息
     * @date: 2020/1/29 11:34
     */
    SUCCESS(201, "操作成功"),

    PASSWORD_ERROR(401, "密码错误"),

    USERNAME_EXIT(405, "用户名已存在"),

    EMAIL_EXIT(406, "邮箱已存在"),

    NEED_LOGIN(407, "用户未登录，请先登录"),

    PARAM_ERROR(408, "参数错误"),

    INTERNAL_ERROR(402, "服务器错误"),

    USERNAME_OR_PASSWORD_ERROR(409, "用户名或者密码错误"),

    PRODUCT_DOWM_OR_DELETE(303, "商品下架或删除"),

    PRODUCT_NOT_EXIST(305, "商品不存在"),

    PRODUCT_STOCK_NOT_ENOUGH(307, "库存不足"),

    CART_PRODUCT_NOT_EXIST(309, "购物车中该商品不存在"),
    ;

    Integer code;

    String desc;
}
