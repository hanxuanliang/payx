package com.hxl.payx.exception.handler;

import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.entity.User;
import com.hxl.payx.exception.UserLoginException;
import com.hxl.payx.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 用户登录 异常处理
 * @Author: hanxuanliang
 * @Date: 2020/1/29 17:55
 */
@RestControllerAdvice
public class UserLoginExceptionHandler{

    @ExceptionHandler(UserLoginException.class)
    public ResponseVo<String> userLoginHandler(UserLoginException exception) {
        // 记得写message，不然然后的json里面没有desc字段
        return ResponseVo.error(ResponseEnum.NEED_LOGIN, exception.getMessage());
    }
}
