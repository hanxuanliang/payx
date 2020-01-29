package com.hxl.payx.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户注册 表单
 * @Author: hanxuanliang
 * @Date: 2020/1/29 15:17
 *
 * 用于集合：@NotEmpty
 * 用于String：@NotBlank
 * 不为Null：@NotNull
 */
@Data
public class UserRegisterForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;
}
