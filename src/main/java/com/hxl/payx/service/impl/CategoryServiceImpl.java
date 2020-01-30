package com.hxl.payx.service.impl;

import com.hxl.payx.constants.MallConst;
import com.hxl.payx.dao.CategoryMapper;
import com.hxl.payx.entity.Category;
import com.hxl.payx.service.ICategoryService;
import com.hxl.payx.vo.CategoryVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类目 实现类
 * @Author: hanxuanliang
 * @Date: 2020/1/29 22:06
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ResponseVo<List<CategoryVo>> selectAllCategory() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAllCategory();
        for (Category category : categories) {
            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)) {
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category, categoryVo);
                categoryVoList.add(categoryVo);
            }
        }
        return ResponseVo.success(categoryVoList);
    }
}
