package com.gdut.pandora.domain.result;

import lombok.Data;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 */
@Data
public class ProductDTO {

    private Integer tradeItemId;

    private String name;

    private String title;

    private String category;

    private String status;

    private String sale;

    private String stock;

    private Integer shopId;

    private String shopName;

    private String mainImage;

    private String price;

    private String[] subImages = new String[]{};
}
