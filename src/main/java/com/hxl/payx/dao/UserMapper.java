package com.hxl.payx.dao;

import com.hxl.payx.entity.User;

/**
 * @Description: 用户 mapper
 * @Author: hanxuanliang
 * @Date: 2020/1/29 8:52
 */
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUsername(String username);

    int countByEmail(String email);

    User selectByUsername(String username);
}
