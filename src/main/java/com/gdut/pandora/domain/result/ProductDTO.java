package com.gdut.pandora.domain.result;

import lombok.Data;

/**
 * Created by buzheng on 18/4/1.
 *
 */
@Data
public class ProductDTO {

    private Integer id;

    private String name;

    private String title;

    private String category;

    private String status;

    private String sale;

    private String stock;

    private Integer shopId;

    private String shopName;

    private String image;

    private String price;

    private Long createTime;

    private Long updateTime;
}
