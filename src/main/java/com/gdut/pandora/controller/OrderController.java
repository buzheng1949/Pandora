package com.gdut.pandora.controller;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.query.OrderQuery;
import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderDetailResult;
import com.gdut.pandora.domain.result.OrderListDataResult;
import com.gdut.pandora.service.OrderDetailService;
import com.gdut.pandora.service.OrderListService;
import com.gdut.pandora.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by buzheng on 18/4/15.
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/list/query")
    public ServerResponse selectOrderList(@RequestParam(required = true, value = "uid") Long id) {
        List<OrderListDataResult> orderListDataResults = new ArrayList<>();
        try {
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setUid(id);
            List<OrderDTO> orderDTOList = orderService.selectOrderList(orderQuery);
            orderListDataResults = orderListService.assembleOrderListDataList(orderDTOList);
        } catch (Exception e) {
            log.error("query the orderList error", e);
        }
        return ServerResponse.createBySuccessMessage(orderListDataResults);
    }


    @RequestMapping(value = "/detail")
    public ServerResponse detail(@RequestParam(value = "orderId", required = true) Long orderId) {
        OrderDetailResult orderDetailResults = new OrderDetailResult();
        try {
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setOrderId(orderId);
            List<OrderDTO> orderDTOList = orderService.selectOrderList(orderQuery);
            orderDetailResults = orderDetailService.getOrderDetail(orderDTOList);
        } catch (Exception e) {
            log.error("query the detail error", e);
        }
        return ServerResponse.createBySuccessMessage(orderDetailResults);
    }

    @RequestMapping("/delete")
    public ServerResponse delete(@RequestParam(value = "orderId", required = true) Long orderId, @RequestParam(required = true, value = "uid") Long uid) {
        List<OrderListDataResult> orderListDataResults = new ArrayList<>();
        try {
            List<OrderDTO> orderDTOList = orderService.deleteOrder(orderId, uid);
            orderListDataResults = orderListService.assembleOrderListDataList(orderDTOList);
        } catch (Exception e) {
            log.error("delete the order error{},the orderId is{}", e, orderId);
        }
        return ServerResponse.createBySuccessMessage(orderListDataResults);
    }

    @RequestMapping("/buy")
    public ServerResponse order(@RequestParam(value = "orderMap", required = true) Map<Integer, Integer> orderMap,
                                @RequestParam(required = true, value = "uid") Long uid,
                                @RequestParam(required = true,value = "createTime") Long createTime) {
        List<OrderListDataResult> orderListDataResults = new ArrayList<>();
        try {
            orderListDataResults = orderService.insertOrder(uid, orderMap,createTime);
        } catch (Exception e) {
            log.error("insert the orderList error", e);
        }
        return ServerResponse.createBySuccessMessage(orderListDataResults);
    }

}
