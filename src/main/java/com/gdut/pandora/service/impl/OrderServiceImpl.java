package com.gdut.pandora.service.impl;

import com.alibaba.fastjson.JSON;
import com.gdut.pandora.domain.Order;
import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.query.OrderQuery;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderListDataResult;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.mapper.OrderMapper;
import com.gdut.pandora.service.OrderListService;
import com.gdut.pandora.service.OrderService;
import com.gdut.pandora.service.ProductService;
import com.gdut.pandora.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.ORB;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RunnableFuture;

/**
 * Created by buzheng on 18/4/14.
 * 订单服务实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderListService orderListService;

    @Override
    public List<OrderListDataResult> insertOrder(Long uid, Map<Integer, Integer> orderMap) {
        if (uid == null) {
            log.error("请传入合法的用户ID");
            throw new RuntimeException("请传入合法的用户ID");
        }
        if (orderMap == null && orderMap.values().size() <= 0) {
            log.error("订单创建失败，请勿传入空的订单信息");
            throw new RuntimeException("订单创建失败，请勿传入空的订单信息");
        }
        Set<Integer> keySet = orderMap.keySet();
        //分别对应个数 用,分隔
        StringBuilder stringNum = new StringBuilder();
        //订单，用,分割
        StringBuilder stringOrder = new StringBuilder();
        //图片 用逗号分隔
        StringBuilder stringImage = new StringBuilder();
        //总数
        Long totalItemNum = 0l;
        Long price = 0L;
        for (Integer itemId : keySet) {
            Integer num = orderMap.get(itemId);
            ProductQuery productQuery = new ProductQuery();
            productQuery.setId(itemId);
            List<ProductDTO> resultProduct = productService.selectProductList(productQuery);
            if (CollectionUtils.isEmpty(resultProduct)) {
                continue;
            }
            totalItemNum += 1;
            ProductDTO productDTO = resultProduct.get(0);
            String image = productDTO.getImage();
            totalItemNum += num;
            price += Long.valueOf(productDTO.getPrice());
            stringImage.append(image).append(",");
            stringNum.append(itemId).append(",");
            stringOrder.append(num).append(",");
        }
        stringOrder.substring(0, stringOrder.length());
        stringNum.substring(0, stringNum.length());
        stringImage.substring(0, stringImage.length());
        Order order = new Order();
        order.setUid(uid);
        order.setPrice(price);
        order.setNums(stringNum.toString());
        order.setTradeItems(stringOrder.toString());
        order.setImage(stringImage.toString());
        order.setNum(totalItemNum);
        order.setCreateTime(TimeUtils.getCurrentTime());
        Integer res = orderMapper.insert(order);
        if (res <= 0) {
            log.error("订单创建失败，请联系开发查询");
            throw new RuntimeException("订单创建失败，请联系开发查询");
        }
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setUid(uid);
        List<OrderDTO> orders = selectOrderList(orderQuery);
        List<OrderListDataResult> orderListDataResults = orderListService.assembleOrderListDataList(orders);
        return orderListDataResults;
    }

    @Override
    public List<OrderDTO> selectOrderList(OrderQuery orderQuery) {
        if (orderQuery == null) {
            log.error("请输入有效的订单查询请求");
            throw new RuntimeException("请输入有效的订单查询请求");
        }
        if (orderQuery.getUid() == null && orderQuery.getOrderId() == null) {
            log.error("请输入合法的用户ID以及订单ID");
            throw new RuntimeException("请输入合法的用户ID以及订单ID");
        }
        List<Order> orderList = orderMapper.select(orderQuery);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (Order order : orderList) {
                OrderDTO orderDTO = order.assembleOrderDTO(order);
                orderDTOList.add(orderDTO);
            }
        }
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> deleteOrder(Long orderId, Long uid) {
        if (orderId == null || orderId <= 0) {
            log.error("请输入合法的订单ID");
            throw new RuntimeException("请输入合法的订单ID");
        }
        int res = orderMapper.deleteByPrimaryKey(orderId.intValue());
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setUid(uid);
        return selectOrderList(orderQuery);
    }


}
