package com.hxl.payx.service;

import com.hxl.payx.vo.CategoryVo;
import com.hxl.payx.vo.ResponseVo;

import java.util.List;
import java.util.Set;

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

    /**
     * 查询子类目
     * @param parentId 类目父id
     * @param resultSet 类目set集合
     * @return void
     * @date: 2020/1/30 11:47
     */
    void findSubCategoryId(Integer parentId, Set<Integer> resultSet);
}
