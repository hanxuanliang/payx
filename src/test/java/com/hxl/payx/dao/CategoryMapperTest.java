package com.hxl.payx.dao;

import com.hxl.payx.PayxApplicationTests;
import com.hxl.payx.entity.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/1/15 9:50
 */
public class CategoryMapperTest extends PayxApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category.toString());
    }

    @Test
    public void queryById() {
        Category category = categoryMapper.queryById(100001);
        System.out.println(category.toString());
    }
}