package com.gdut.pandora.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by buzheng on 18/4/14.
 * 商品详情页类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDetailResult {

    private Integer tradeItemId;

    private String category;

    private String name;

    private String[] subImages = new String[]{};

    private String mainImage;

    private String title;

    private Integer price;

    private Integer status;

    private Integer sale;

    private Integer stock;

    private Integer shopId;

    private String shopNmae;

}
