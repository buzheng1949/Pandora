package com.gdut.pandora.service;

import com.gdut.pandora.domain.Order;
import com.gdut.pandora.domain.query.OrderQuery;
import com.gdut.pandora.domain.result.OrderDTO;

import java.util.List;

/**
 * Created by buzheng on 18/4/14.
 * 订单接口类
 */
public interface OrderService {

    /**
     * 插入订单
     *
     * @param order 订单
     * @return 返回订单信息
     */
    OrderDTO insertOrder(Order order);

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
    List<OrderDTO> deleteOrder(Long orderId);

}
