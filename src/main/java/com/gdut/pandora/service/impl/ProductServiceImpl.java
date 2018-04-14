package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductActionResult;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.mapper.ProductMapper;
import com.gdut.pandora.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
        List<ProductDTO> productList = selectProductList(productQuery);
        ProductActionResult productActionResult = assembleProductActionResult(productList, productQuery);
        return productActionResult;
    }

    /**
     * 从数据库获取原始商品列表
     *
     * @param productQuery
     * @return
     */
    public List<ProductDTO> selectProductList(ProductQuery productQuery) {
        ProductQuery query = productQuery;
        if (productQuery == null) {
            query = new ProductQuery();
        }
        List<Product> productList = productMapper.fetch(query);
        List<ProductDTO> productDTOList = assembleProductDTOs(productList);
        return productDTOList;
    }

    /**
     * 构建前端返回数据结果集合
     *
     * @param productDTOList
     * @param productQuery
     * @return
     */
    private ProductActionResult assembleProductActionResult(List<ProductDTO> productDTOList, ProductQuery productQuery) {
        ProductActionResult productActionResult = new ProductActionResult();
        Integer currentPage = productQuery.getPage();
        productActionResult.setCurrentPage(currentPage);
        productActionResult.setPage(currentPage);
        productActionResult.setPageSize(productQuery.getPageSize());
        productActionResult.setTotal(productDTOList == null ? 0 : productDTOList.size());
        productActionResult.setEnd(CollectionUtils.isEmpty(productDTOList) || productDTOList.size() < productQuery.getPageSize() ? Boolean.TRUE : Boolean.FALSE);
        productActionResult.setNextPage(currentPage + 1);
        productActionResult.setList(productDTOList);
        return productActionResult;
    }

    /**
     * 将product转换为productdto
     * @param productList
     * @return
     */
    private List<ProductDTO> assembleProductDTOs(List<Product> productList) {
        List<ProductDTO> resultDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productList)) {
            for (Product product : productList) {
                ProductDTO productDTO = new ProductDTO();
                BeanUtils.copyProperties(product, productDTO);
                if (!StringUtils.isEmpty(product.getImages())){
                    String[] subImages = product.getImages().split("&");
                    productDTO.setSubImages(subImages);
                }
                resultDtos.add(productDTO);
            }
        }
        return resultDtos;
    }


}
