package com.hxl.payx.service;

import com.hxl.payx.entity.User;
import com.hxl.payx.vo.ResponseVo;

/**
 * @Description: 用户 service
 * @Author: hanxuanliang
 * @Date: 2020/1/29 9:02
 */
public interface IUserService {
    /**
     * 注册
     * @param user 用户
     * @return void
     * @date: 2020/1/29 9:03
     */
    ResponseVo<User> register(User user);

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return ResponseVo 统一返回对象
     * @date: 2020/1/29 16:27
     */
    ResponseVo<User> login(String username, String password);
}
