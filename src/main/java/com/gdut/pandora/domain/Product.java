package com.gdut.pandora.domain;

import lombok.Data;

/**
 * 商品实体类
 */
@Data
public class Product {
    private Integer id;

    private String name;

    private String title;

    private String category;

    private String status;

    private String sale;

    private String stock;

    private String shopName;

    private Integer shopId;

    private String image;

    private String price;

    private Long createTime;

    private Long updateTime;



}