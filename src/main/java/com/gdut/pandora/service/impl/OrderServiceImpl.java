package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Order;
import com.gdut.pandora.domain.query.OrderQuery;
import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buzheng on 18/4/14.
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderDTO insertOrder(Order order) {
        return null;
    }

    @Override
    public List<OrderDTO> selectOrderList(OrderQuery orderQuery) {
        return null;
    }

    @Override
    public List<OrderDTO> deleteOrder(Long orderId) {
        return null;
    }
}
