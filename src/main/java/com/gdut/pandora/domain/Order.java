package com.gdut.pandora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单实体接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;

    private Long price;

    private Long uid;

    private Long createTime;

    private String image;

    private Long num;

    private String tradeItems;

    private String nums;
}