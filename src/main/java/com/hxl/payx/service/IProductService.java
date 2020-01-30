package com.hxl.payx.service;

import com.github.pagehelper.PageInfo;
import com.hxl.payx.vo.ProductDetailVo;
import com.hxl.payx.vo.ProductVo;
import com.hxl.payx.vo.ResponseVo;

/**
 * @Description: 商品 service
 * @Author: hanxuanliang
 * @Date: 2020/1/30 12:06
 */
public interface IProductService {

    ResponseVo<PageInfo<ProductVo>> getPageOfProduct(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> getProductDetail(Integer productId);
}
