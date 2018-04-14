package com.gdut.pandora.controller;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductActionResult;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.domain.result.ProductDetailResult;
import com.gdut.pandora.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

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
            return ServerResponse.createByErrorMessage("查询商品列表失败", productActionResult);
        }
        return ServerResponse.createBySuccessMessage(productActionResult);
    }

    @RequestMapping("/detail")
    public ServerResponse<ProductDetailResult> queryDetail(Integer tradeItemId) {
        ProductDetailResult productDetailResult = new ProductDetailResult();
        try {
            if (tradeItemId == null || tradeItemId <= 0) {
                return ServerResponse.createByErrorMessage("请传入具体的商品ID", productDetailResult);
            }
            ProductQuery productQuery = new ProductQuery();
            productQuery.setId(tradeItemId);
            List<ProductDTO> productList = productService.selectProductList(productQuery);
            if (!CollectionUtils.isEmpty(productList)) {
                ProductDTO product = productList.get(0);
                if (product != null) {
                    productDetailResult.setCategory(product.getCategory());
                    productDetailResult.setMainImage(product.getImage());
                    productDetailResult.setName(product.getName());
                    productDetailResult.setPrice(product.getPrice());
                    productDetailResult.setSale(product.getSale());
                    productDetailResult.setShopId(product.getShopId());
                    productDetailResult.setStock(product.getStock());
                    productDetailResult.setStatus(product.getStatus());
                    productDetailResult.setTradeItemId(product.getId());
                    productDetailResult.setTitle(product.getTitle());
                    productDetailResult.setSubImages(product.getSubImages());
                    productDetailResult.setShopNmae(product.getShopName());
                }
                return ServerResponse.createBySuccess("success", productDetailResult);
            }
        } catch (Exception e) {
            return ServerResponse.createBySuccess("查询失败，请联系开发查询", productDetailResult);
        }
        return ServerResponse.createBySuccess("查询成功", productDetailResult);
    }
}
