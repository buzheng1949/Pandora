package com.gdut.pandora.domain.result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 * 订单详情类
 */
public class OrderDetailResult {

    @Getter
    @Setter
    private Long orderId;

    @Getter
    @Setter
    private Long price;

    @Getter
    @Setter
    private Long createTime;

    @Getter
    @Setter
    private List<Good> goods;


    /**
     * 商品
     */
    @Data
    public static class Good {
        private String productName;

        private Integer price;

        private String image;

        private Integer num;
    }
}
