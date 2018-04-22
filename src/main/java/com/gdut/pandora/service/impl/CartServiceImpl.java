package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Cart;
import com.gdut.pandora.domain.query.CartQuery;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.CartDTO;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.mapper.CartMapper;
import com.gdut.pandora.service.CartService;
import com.gdut.pandora.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<CartDTO> list(CartQuery cartQuery) {
        if (cartQuery == null) {
            throw new RuntimeException("不能传入空的查询参数");
        }
        if (cartQuery.getUid() == null || cartQuery.getUid() <= 0) {
            throw new RuntimeException("请传入用户ID");
        }
        List<CartDTO> res = new ArrayList<>();
        List<Cart> cartList = cartMapper.select(cartQuery);
        if (CollectionUtils.isEmpty(cartList)) {
            return res;
        }
        for (Cart cart : cartList) {
            CartDTO cartDTO = new CartDTO();
            BeanUtils.copyProperties(cart, cartDTO);
            res.add(cartDTO);
        }
        return res;
    }

    @Override
    public boolean delete(Long uid, Integer id) {
        if (uid == null || id == null) {
            throw new RuntimeException("请检查用户的ID或者需要删除的购物车ID");
        }
        int res = cartMapper.deleteByPrimaryKey(id);
//        CartQuery cartQuery = new CartQuery();
//        cartQuery.setUid(uid);
        return res > 0;
    }

    @Override
    public boolean insert(Cart cart) {
        if (cart.getUid() == null
                || cart.getItemId() == null
                ) {
            throw new RuntimeException("请检查传入参数是否完整");
        }
        CartQuery cartQuery = new CartQuery();
        cartQuery.setUid(cart.getUid());
        cartQuery.setItemId(cart.getItemId());
        List<CartDTO> cartDTOs = list(cartQuery);
        if (!CollectionUtils.isEmpty(cartDTOs)) {
            CartDTO cartDTO = cartDTOs.get(0);
            boolean result = update(cartDTO.getId(), 1,cartDTO.getUid());
            return result;
        }
        Integer itemId = cart.getItemId();
        ProductQuery productQuery = new ProductQuery();
        productQuery.setId(itemId);
        List<ProductDTO> productDTOList = productService.selectProductList(productQuery);
        if (CollectionUtils.isEmpty(productDTOList)) {
            return false;
        }
        ProductDTO productDTO = productDTOList.get(0);
        cart.setImage(productDTO.getMainImage());
        cart.setPrice(Integer.valueOf(productDTO.getPrice()));
        cart.setShopId(productDTO.getShopId().longValue());
        cart.setTitle(productDTO.getTitle());
        cart.setShopName(productDTO.getShopName());
        cart.setNum(1);
        int res = cartMapper.insert(cart);
        return res > 0;
    }

    @Override
    public boolean update(Integer id, Integer isAdd,Long uid) {
        if (id == null || isAdd == null) {
            throw new RuntimeException("请检查购物车ID以及是否进行加减操作");
        }
        CartQuery cartQuery = new CartQuery();
        cartQuery.setId(id);
        cartQuery.setUid(uid);
        List<CartDTO> cartDTOs = list(cartQuery);
        if (CollectionUtils.isEmpty(cartDTOs)) {
            return false;
        }
        CartDTO cartDTO = cartDTOs.get(0);
        Integer num = cartDTO.getNum();
        if (isAdd == 1) {
            num = num + 1;
        } else {
            num = num - 1;
        }
        cartQuery.setNum(num);
        int res = cartMapper.update(cartQuery);
        return res > 0l;
    }
}
