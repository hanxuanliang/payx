package com.hxl.payx.service;

import com.hxl.payx.vo.ProductVo;
import com.hxl.payx.vo.ResponseVo;

import java.util.List;

/**
 * @Description: 商品 service
 * @Author: hanxuanliang
 * @Date: 2020/1/30 12:06
 */
public interface IProductService {

    ResponseVo<List<ProductVo>> getPageOfProduct(Integer categoryId, Integer pageNum, Integer pageSize);
}
