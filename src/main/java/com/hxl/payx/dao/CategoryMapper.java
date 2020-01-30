package com.hxl.payx.dao;

import com.hxl.payx.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 商品分类表的Mapper映射
 * @Author: hanxuanliang
 * @Date: 2020/1/15 8:55
 */
@Repository
public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAllCategory();
}
