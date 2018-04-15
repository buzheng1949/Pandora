package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/14.
 */
@Data
public class OrderQuery {

    /**
     * 通过订单ID查询
     */
    private Long orderId;

    /**
     * 通过用户ID查询
     */
    private Long uid;
}
