package com.gdut.pandora.controller;

import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.result.AddressDTO;
import com.gdut.pandora.domain.result.ShopActionResult;
import com.gdut.pandora.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 */
@RestController
@ResponseBody
@RequestMapping("/shop")
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("/query")
    @ReturnType
    public ServerResponse query(@RequestParam(value = "shopId") Integer shopId) {
        ShopActionResult shopActionResult = new ShopActionResult();
        try {
            shopActionResult = shopService.fetch(shopId);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(e.getMessage(), Boolean.FALSE);
        }
        return ServerResponse.createBySuccess("查询成功", shopActionResult);
    }

}
