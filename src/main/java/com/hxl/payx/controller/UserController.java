package com.hxl.payx.controller;

import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.entity.User;
import com.hxl.payx.form.UserForm;
import com.hxl.payx.service.IUserService;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Description: 用户 Controller
 * @Author: hanxuanliang
 * @Date: 2020/1/29 10:48
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /**
     * 表单提交使用：@RequestParam(value = "username") String username
     * json：@RequestBody User user
     * json 是组装成一个整体json字符串
     */
    @PostMapping("/register")
    public ResponseVo register(@RequestBody UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            log.error("注册提交的参数有误：{}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return iUserService.register(user);
    }
}
