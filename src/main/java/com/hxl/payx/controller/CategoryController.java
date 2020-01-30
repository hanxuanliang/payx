package com.hxl.payx.controller;

import com.hxl.payx.service.ICategoryService;
import com.hxl.payx.vo.CategoryVo;
import com.hxl.payx.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 类目 controller
 * @Author: hanxuanliang
 * @Date: 2020/1/29 22:18
 */
@RestController
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAllCategory() {
        return categoryService.selectAllCategory();
    }
}
