package com.gdut.pandora.service;

import com.gdut.pandora.domain.Shop;
import com.gdut.pandora.domain.result.ShopActionResult;

/**
 * Created by buzheng on 18/4/21.
 */
public interface ShopService {

    /**
     * 查询店铺信息
     *
     * @param shopId
     * @return
     */
    Shop list(Integer shopId);

    /**
     * 查询店铺以及下面所有的商品信息
     *
     * @param shopId
     * @return
     */
    ShopActionResult fetch(Integer shopId);
}
