package com.gdut.pandora.domain.result;

import lombok.Data;

import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 * 对外透出结果类
 */
@Data
public class CartActionResult {

    private String shopName;

    private List<CartDTO> carts;

}
