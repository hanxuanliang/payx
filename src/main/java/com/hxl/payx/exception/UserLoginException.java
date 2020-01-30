package com.hxl.payx.exception;

/**
 * @Description: 用户登录 异常
 * @Author: hanxuanliang
 * @Date: 2020/1/29 18:02
 */
public class UserLoginException extends RuntimeException {
    public UserLoginException(String message) {
        super(message);
    }
}
