package com.hxl.payx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxl.payx.constants.ProductStatusEnum;
import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.dao.ProductMapper;
import com.hxl.payx.entity.Product;
import com.hxl.payx.service.ICategoryService;
import com.hxl.payx.service.IProductService;
import com.hxl.payx.vo.ProductDetailVo;
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
    public ResponseVo<PageInfo<ProductVo>> getPageOfProduct(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        // 只需要这一行代码就好，tql
        PageHelper.startPage(pageNum, pageSize);
        List<ProductVo> productVoList = productMapper.findByCategoryIds(categoryIdSet).stream()
                .map(c -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(c, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        PageInfo<ProductVo> pageInfo = new PageInfo<>(productVoList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        // if的判断建议在越早判断就早结束return，只对确定性的条件进行判断；
        // 之说以没有写!来判断，是因为如果之后出现新的枚举状态就要改！的if判断，破坏结构性
        if (product.getStatus().equals(ProductStatusEnum.DOWN_SALE.getCode()) ||
                product.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_DOWM_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        // 库存敏感数据处理
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
