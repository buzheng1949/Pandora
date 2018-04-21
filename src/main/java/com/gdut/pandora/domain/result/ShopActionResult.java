package com.gdut.pandora.domain.result;

import lombok.Data;

import java.util.List;

/**
 * Created by buzheng on 18/4/21.
 * 对外透出的店铺信息集合
 */
@Data
public class ShopActionResult {

    private String shopName;

    private Integer star;

    private Integer fans;

    private String content;

    private String image;

    private List<ProductDTO> pros;
}
