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

    private String price;

    private String status;

    private String sale;

    private String stock;

    private Integer shopId;

    private String shopNmae;

}
