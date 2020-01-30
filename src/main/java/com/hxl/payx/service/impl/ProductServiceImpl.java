package com.hxl.payx.service.impl;

import com.hxl.payx.dao.ProductMapper;
import com.hxl.payx.service.ICategoryService;
import com.hxl.payx.service.IProductService;
import com.hxl.payx.vo.ProductVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 商品 service实现类
 * @Author: hanxuanliang
 * @Date: 2020/1/30 12:08
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductMapper productMapper;

    private final ICategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ICategoryService categoryService) {
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ResponseVo<List<ProductVo>> getPageOfProduct(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        List<ProductVo> productVoList = productMapper.findByCategoryIds(categoryIdSet).stream()
                .map(c -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(c, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        return ResponseVo.success(productVoList);
    }
}
