package com.hxl.payx.dao;

import com.hxl.payx.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Description: 商品 mapper
 * @Author: hanxuanliang
 * @Date: 2020/1/30 12:01
 */
@Repository
public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    // 非基本类型需要使用@Param指定参数是什么
    List<Product> findByCategoryIds(@Param("categoryIdSet") Set<Integer> categoryIdSet);
}
