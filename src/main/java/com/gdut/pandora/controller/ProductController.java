package com.gdut.pandora.controller;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductActionResult;
import com.gdut.pandora.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by buzheng on 18/4/1.
 */
@RestController
@ResponseBody
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping("/list")
    public ServerResponse<ProductActionResult> query(ProductQuery query) {
        ProductActionResult productActionResult = new ProductActionResult();
        try {
            if (query.getPage() == null || query.getPageSize() == null) {
                query.setPage(1);
                query.setPageSize(10);
            }
            query.setStart((query.getPage() - 1) * (query.getPageSize()));
            productActionResult = productService.fetchProductList(query);
        } catch (Exception e) {
            log.error("query the product list error", e);
            return ServerResponse.createByErrorMessage("查询商品列表失败");
        }
        return ServerResponse.createBySuccessMessage(productActionResult);
    }
}
