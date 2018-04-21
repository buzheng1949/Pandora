package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Cart;
import com.gdut.pandora.domain.query.CartQuery;
import com.gdut.pandora.domain.result.CartDTO;
import com.gdut.pandora.service.CartService;

import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 */
public class CartServiceImpl implements CartService {

    @Override
    public List<CartDTO> list(CartQuery cartQuery) {
        return null;
    }

    @Override
    public List<CartDTO> delete(Long id) {
        return null;
    }

    @Override
    public boolean insert(Cart cart) {
        return false;
    }

    @Override
    public boolean update(CartQuery cartQuery) {
        return false;
    }
}
