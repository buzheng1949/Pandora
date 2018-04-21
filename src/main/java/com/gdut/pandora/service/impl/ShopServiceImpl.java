package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Shop;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.domain.result.ShopActionResult;
import com.gdut.pandora.mapper.ShopMapper;
import com.gdut.pandora.service.ProductService;
import com.gdut.pandora.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ProductService productService;


    @Override
    public Shop list(Integer shopId) {
        if (shopId == null) {
            throw new RuntimeException("请传入店铺ID");
        }
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        return shop;
    }

    @Override
    public ShopActionResult fetch(Integer shopId) {
        Shop shop = list(shopId);
        ShopActionResult shopActionResult = new ShopActionResult();
        shopActionResult.setShopName(shop.getShopName());
        shopActionResult.setContent(shop.getContent());
        shopActionResult.setFans(shop.getFans());
        shopActionResult.setImage(shop.getImage());
        shopActionResult.setStar(shop.getStar());
        ProductQuery productQuery = new ProductQuery();
        productQuery.setShopId(shopId);
        List<ProductDTO> productDTOList = productService.selectProductList(productQuery);
        shopActionResult.setPros(productDTOList);
        return shopActionResult;
    }
}
