package com.hxl.payx.dao;

import com.hxl.payx.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: 商品分类表的Mapper映射
 * @Author: hanxuanliang
 * @Date: 2020/1/15 8:55
 */
public interface CategoryMapper {

    /**
     * Id 查找对应分类
     * @param id
     * @return Category
     * @date: 2020/1/15 8:58
     */
    @Select("select * from mall_category where id = #{id}")
    Category findById(@Param("id") Integer id);

    /**
     * 使用xml方式 Id 查找对应的分类
     * @param id
     * @return Category
     * @date: 2020/1/15 9:31
     */
    Category queryById(Integer id);
}
