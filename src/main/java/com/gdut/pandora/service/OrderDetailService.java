package com.gdut.pandora.service;

import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderDetailResult;

import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 */
public interface OrderDetailService {

    /**
     * 查询单个对外透出的订单实体
     *
     * @param orderDTOs
     * @return
     */
    OrderDetailResult getOrderDetail(List<OrderDTO> orderDTOs);
}
