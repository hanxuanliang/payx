package com.hxl.payx.service;

import com.hxl.payx.vo.CategoryVo;
import com.hxl.payx.vo.ResponseVo;

import java.util.List;

/**
 * @Description: 类目 service
 * @Author: hanxuanliang
 * @Date: 2020/1/29 22:03
 */
public interface ICategoryService {
    /**
     * 查询所有的类目
     * @return ResponseVo<List<CategoryVo>> 统一返回对象封装
     * @date: 2020/1/29 22:05
     */
    ResponseVo<List<CategoryVo>> selectAllCategory();
}
