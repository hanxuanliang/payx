package com.hxl.payx.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 角色 枚举
 * @Author: hanxuanliang
 * @Date: 2020/1/29 16:00
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 角色枚举
     * @date: 2020/1/29 16:01
     */
    ADMIN(1),
    CUSTOMER(0),
    ;

    Integer code;
}
