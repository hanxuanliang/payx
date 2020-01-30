package com.hxl.payx.controller;

import com.github.pagehelper.PageInfo;
import com.hxl.payx.service.IProductService;
import com.hxl.payx.vo.ProductDetailVo;
import com.hxl.payx.vo.ProductVo;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 商品 controller
 * @Author: hanxuanliang
 * @Date: 2020/1/30 14:58
 */
@RestController
@Slf4j
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/pages/{categoryId}", "/pages"})
    public ResponseVo<PageInfo<ProductVo>> getPageOfProduct(@PathVariable(required = false) Integer categoryId,
                                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return productService.getPageOfProduct(categoryId, pageNum, pageSize);
    }

    @GetMapping("/product/{productId}")
    public ResponseVo<ProductDetailVo> getProductDetail(@PathVariable Integer productId) {
        return productService.getProductDetail(productId);
    }
}
