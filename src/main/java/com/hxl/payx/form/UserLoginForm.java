package com.hxl.payx.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户登录 表单
 * @Author: hanxuanliang
 * @Date: 2020/1/29 16:38
 */
@Data
public class UserLoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
