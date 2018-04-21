package com.gdut.pandora.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by buzheng on 18/4/21.
 * 购物车查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartQuery extends BaseQuery{
    private Integer id;

    private Long shopId;

    private String shopName;

    private Integer itemId;

    private String image;

    private String title;

    private Integer num;

    private Integer price;

    private Long uid;
}
