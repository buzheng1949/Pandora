package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.ProductQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * 根据条件查询商品列表
     *
     * @param productQuery
     * @return
     */
    List<Product> fetch(ProductQuery productQuery);
}