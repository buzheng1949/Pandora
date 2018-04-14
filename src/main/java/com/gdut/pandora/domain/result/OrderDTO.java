package com.gdut.pandora.domain.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by buzheng on 18/4/14.
 * 对外订单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;

    private Long price;

    private Long uid;

    private Long createTime;

    private String image;

    private Long num;

    private String tradeItems;

    private String nums;
}
