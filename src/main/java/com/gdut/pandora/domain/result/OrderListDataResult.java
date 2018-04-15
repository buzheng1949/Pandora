package com.gdut.pandora.domain.result;

import lombok.Data;

import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 * 订单列表对外透出类
 */
@Data
public class OrderListDataResult {

    private Long orderId;

    private Long price;

    private Long createTime;

    private String[] image;
}
