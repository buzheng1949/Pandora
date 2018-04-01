package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductActionResult;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.mapper.ProductMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductActionResult fetchProductList(ProductQuery productQuery) {
        List<Product> productList = selectProductList(productQuery);
        ProductActionResult productActionResult = assembleProductActionResult(productList, productQuery);
        return productActionResult;
    }

    /**
     * 从数据库获取原始商品列表
     *
     * @param productQuery
     * @return
     */
    private List<Product> selectProductList(ProductQuery productQuery) {
        ProductQuery query = productQuery;
        if (productQuery == null) {
            query = new ProductQuery();
        }
        List<Product> productList = productMapper.fetch(query);
        return productList;
    }

    /**
     * 构建前端返回数据结果集合
     *
     * @param productList
     * @param productQuery
     * @return
     */
    private ProductActionResult assembleProductActionResult(List<Product> productList, ProductQuery productQuery) {
        ProductActionResult productActionResult = new ProductActionResult();
        List<ProductDTO> resultDtos = Lists.newArrayList();
        Integer currentPage = productQuery.getPage();
        productActionResult.setCurrentPage(currentPage);
        productActionResult.setPage(currentPage);
        productActionResult.setPageSize(productQuery.getPageSize());
        productActionResult.setTotal(productList == null ? 0 : productList.size());
        productActionResult.setEnd(CollectionUtils.isEmpty(productList) || productList.size() < productQuery.getPageSize() ? Boolean.TRUE : Boolean.FALSE);
        productActionResult.setNextPage(currentPage + 1);
        if (CollectionUtils.isNotEmpty(productList)) {
            for (Product product : productList) {
                ProductDTO productDTO = new ProductDTO();
                BeanUtils.copyProperties(product, productDTO);
                productDTO.setTradeItemId(product.getId());
                resultDtos.add(productDTO);
            }
        }
        productActionResult.setList(resultDtos);
        return productActionResult;
    }


}
