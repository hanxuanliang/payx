package com.hxl.payx.exception.handler;

import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.entity.User;
import com.hxl.payx.exception.UserLoginException;
import com.hxl.payx.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 用户登录 异常处理
 * @Author: hanxuanliang
 * @Date: 2020/1/29 17:55
 */
@ControllerAdvice
public class UserLoginExceptionHandler{

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo<User> userLoginHandler() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
}
