package com.hxl.payx.controller;

import com.hxl.payx.constants.MallConst;
import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.entity.User;
import com.hxl.payx.form.UserLoginForm;
import com.hxl.payx.form.UserRegisterForm;
import com.hxl.payx.service.IUserService;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @Description: 用户 Controller
 * @Author: hanxuanliang
 * @Date: 2020/1/29 10:48
 */
@Slf4j
@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.userService = iUserService;
    }

    /**
     * 表单提交使用：@RequestParam(value = "username") String username
     * json：@RequestBody User user
     * json 是组装成一个整体json字符串
     */
    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm, BindingResult result) {
        if (result.hasErrors()) {
            log.error("注册提交的参数有误：{}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.error("登录提交的参数有误：{}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        // 设置Session
        HttpSession session = request.getSession();
        session.setAttribute(MallConst.CURRENTUSER, userResponseVo.getData());
        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> getUserInfo(HttpSession session) {
        log.info("/user sessionId={}", session.getId());
        User currentUser = (User) session.getAttribute(MallConst.CURRENTUSER);

        return ResponseVo.success(currentUser);
    }

    @PostMapping("/user/logout")
    public ResponseVo<User> logout(HttpSession session) {
        log.info("/user/logout sessionId={}", session.getId());
        session.removeAttribute(MallConst.CURRENTUSER);
        return ResponseVo.success();
    }
}
