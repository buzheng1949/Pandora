package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/1.
 * 商品查询条件类
 */
@Data
public class ProductQuery extends BaseQuery {

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

    private String images;

    private String price;

    private Long createTime;

    private Long updateTime;

    private Integer page;

}
