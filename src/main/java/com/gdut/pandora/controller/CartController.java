package com.gdut.pandora.controller;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Cart;
import com.gdut.pandora.domain.query.CartQuery;
import com.gdut.pandora.domain.result.CartActionResult;
import com.gdut.pandora.domain.result.CartDTO;
import com.gdut.pandora.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by buzheng on 18/4/21.
 * 购物车
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/list")
    public ServerResponse list(@RequestParam(value = "uid") Long uid) {
        List<CartActionResult> res = new ArrayList<>();
        if (uid == null || uid < 0) {
            return ServerResponse.createByErrorMessage("请输入合法的用户ID", res);
        }
        CartQuery cartQuery = new CartQuery();
        cartQuery.setUid(uid);
        List<CartDTO> cartDTOs = cartService.list(cartQuery);
        if (CollectionUtils.isEmpty(cartDTOs)) {
            return ServerResponse.createBySuccessMessage(res);
        }
        Set<String> shopNames = new HashSet<>();
        for (CartDTO cartDTO : cartDTOs) {
            shopNames.add(cartDTO.getShopName());
        }
        Iterator<String> iterator = shopNames.iterator();
        while (iterator.hasNext()) {
            CartActionResult cartActionResult = new CartActionResult();
            String shopName = iterator.next();
            cartActionResult.setShopName(shopName);
            List<CartDTO> dtos = new ArrayList<>();
            Iterator<CartDTO> cartDTOIterator = cartDTOs.iterator();
            while (cartDTOIterator.hasNext()) {
                CartDTO cartDTO = cartDTOIterator.next();
                if (cartDTO.getShopName().equals(shopName)) {
                    dtos.add(cartDTO);
                }
            }
            cartActionResult.setCarts(dtos);
            res.add(cartActionResult);
        }
        return ServerResponse.createBySuccessMessage(res);
    }

    @RequestMapping(value = "/add")
    public ServerResponse insert(@RequestParam(value = "uid") Long uid,
                                 @RequestParam(value = "itemId") Integer itemId) {

        Cart cart = new Cart();
        cart.setUid(uid);
        cart.setItemId(itemId);
        boolean res = cartService.insert(cart);
        if (res) {
            return ServerResponse.createBySuccessMessage(res);
        }
        return ServerResponse.createByErrorMessage("加入购物车失败", Boolean.FALSE);
    }

    @RequestMapping(value = "/update")
    public ServerResponse update(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "isAdd") Integer isAdd,
                                 @RequestParam(value = "uid") Long uid
                                 ) {

        if (id == null || isAdd == null) {
            return ServerResponse.createByErrorMessage("请检查购物车ID以及是否进行加减操作", Boolean.FALSE);
        }
        boolean res = cartService.update(id, isAdd,uid);
        if (res) {
            return ServerResponse.createByErrorMessage("success", res);
        }
        return ServerResponse.createByErrorMessage("更新购物车成功", Boolean.FALSE);
    }

    @RequestMapping(value = "/delete")
    public ServerResponse delete(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "uid") Long uid) {

        if (id == null) {
            return ServerResponse.createByErrorMessage("请检查购物车ID是否传入", Boolean.FALSE);
        }
        boolean res = cartService.delete(uid, id);
        if (res) {
            return ServerResponse.createByErrorMessage("删除成功", res);
        }
        return ServerResponse.createByErrorMessage("删除失败", Boolean.FALSE);
    }


}
