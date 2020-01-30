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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Category> categories = categoryMapper.selectAllCategory();
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(c -> c.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer parentId, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAllCategory();
        findSubCategoryId(parentId, resultSet, categories);
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo catogoryVo : categoryVoList) {
            // 切记尽量不要在for里面进行http请求，sql查询，因为每一次循环都要进行IO连接，这是十分耗时的。
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                if (catogoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                catogoryVo.setSubCategories(subCategoryVoList);
                // 递归查询子目录
                findSubCategory(subCategoryVoList, categories);
            }
        }
    }

    private void findSubCategoryId(Integer parentId, Set<Integer> resultSet, List<Category> categories) {
        for (Category category: categories) {
            if (category.getParentId().equals(parentId)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), resultSet, categories);
            }
        }
    }
}
