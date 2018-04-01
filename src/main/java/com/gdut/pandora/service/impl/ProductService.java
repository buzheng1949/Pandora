package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductActionResult;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 * 商品查询服务
 */
public interface ProductService {

    /**
     * 查询商品列表接口
     *
     * @param productQuery
     * @return
     */
    ProductActionResult fetchProductList(ProductQuery productQuery);
}
