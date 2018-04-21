package com.gdut.pandora.domain;

import lombok.Data;

/**
 * 购物车实体类
 */
@Data
public class Cart {
    private Integer id;

    private Long shopId;

    private String shopName;

    private Integer itemId;

    private String image;

    private String title;

    private Integer num;

    private Integer price;

    private Integer uid;

}