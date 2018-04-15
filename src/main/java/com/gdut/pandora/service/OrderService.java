package com.gdut.pandora.service;

import com.gdut.pandora.domain.Order;
import com.gdut.pandora.domain.query.OrderQuery;
import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderListDataResult;

import java.util.List;
import java.util.Map;

/**
 * Created by buzheng on 18/4/14.
 * 订单接口类
 */
public interface OrderService {

    /**
     * 插入订单
     *
     * @param uid 用户ID
     * @return 订单商品ID以及个数信息
     */
    List<OrderListDataResult> insertOrder(Long uid, Map<Integer, Integer> orderMap);

    /**
     * 查询订单
     *
     * @param orderQuery 订单查询类
     * @return 订单列表
     */
    List<OrderDTO> selectOrderList(OrderQuery orderQuery);

    /**
     * 删除订单
     *
     * @param orderId 需要删除的订单ID
     * @return
     */
    List<OrderDTO> deleteOrder(Long orderId, Long uid);

}
