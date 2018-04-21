package com.gdut.pandora.service;

import com.gdut.pandora.domain.Cart;
import com.gdut.pandora.domain.query.CartQuery;
import com.gdut.pandora.domain.result.CartDTO;

import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 * 购物车服务类
 */
public interface CartService {

    /**
     * 列出购物车商品服务
     *
     * @param cartQuery
     * @return
     */
    List<CartDTO> list(CartQuery cartQuery);

    /**
     * 删除列表服务类
     *
     * @param uid 用户ID
     * @param id
     * @return
     */
    boolean delete(Long uid,Integer id);

    /**
     * 加入购物车
     *
     * @param cart
     * @return
     */
    boolean insert(Cart cart);

    /**
     * 增加或者减少购物车数量
     *
     * @param id
     * @return
     */
    boolean update(Integer id,Integer update,Long uid);
}
