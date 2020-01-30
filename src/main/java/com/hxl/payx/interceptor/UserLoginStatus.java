package com.hxl.payx.interceptor;

import com.hxl.payx.constants.MallConst;
import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.entity.User;
import com.hxl.payx.exception.UserLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户登陆状态拦截验证
 * @Author: hanxuanliang
 * @Date: 2020/1/29 17:43
 */
@Slf4j
public class UserLoginStatus implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("user login status Interceptor ... preHandle...");
        User currentUser = (User) request.getSession().getAttribute(MallConst.CURRENTUSER);
        if (currentUser == null) {
            log.error("user login Status is Blank");
            // 交由全局异常，其实也是AOP处理方式
            throw new UserLoginException(ResponseEnum.NEED_LOGIN.getDesc());
        }
        return true;
    }
}
