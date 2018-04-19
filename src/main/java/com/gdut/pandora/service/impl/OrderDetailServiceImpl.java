package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderDetailResult;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.service.OrderDetailService;
import com.gdut.pandora.service.OrderService;
import com.gdut.pandora.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 * 订单详情页具体实现类
 */
@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {


    @Autowired
    private ProductService productService;

    @Override
    public OrderDetailResult getOrderDetail(List<OrderDTO> orderDTOs) {
//        List<OrderDetailResult> orderDetailResults = new ArrayList<>();
        OrderDetailResult orderDetailResult = new OrderDetailResult();
        try {
            if (CollectionUtils.isEmpty(orderDTOs)) {
                return orderDetailResult;
            }
            OrderDTO orderDTO = orderDTOs.get(0);
            if (orderDTO == null) {
                return orderDetailResult;
            }
            orderDetailResult.setPrice(orderDTO.getPrice());
            orderDetailResult.setCreateTime(orderDTO.getCreateTime());
            orderDetailResult.setOrderId(orderDTO.getOrderId());
            List<OrderDetailResult.Good> goods = new ArrayList<>();
            String[] items = orderDTO.getTradeItems().split(",");
            int index = 0;
            for (String item : items) {
                OrderDetailResult.Good good = new OrderDetailResult.Good();
                Integer itemId = Integer.valueOf(item);
                ProductQuery productQuery = new ProductQuery();
                productQuery.setId(itemId);
                List<ProductDTO> resultProduct = productService.selectProductList(productQuery);
                if (CollectionUtils.isEmpty(resultProduct)) {
                    continue;
                }
                ProductDTO productDTO = resultProduct.get(0);
                good.setProductName(productDTO.getName());
                good.setImage(productDTO.getImage());
                good.setPrice(productDTO.getPrice());
                Integer num = Integer.valueOf(orderDTO.getNums().split(",")[index]);
                index++;
                good.setNum(num);
                Integer price = Integer.valueOf(productDTO.getPrice());
                price = price * num;
                good.setPrice(String.valueOf(price));
                goods.add(good);
            }
            orderDetailResult.setGoods(goods);
//            orderDetailResults.add(orderDetailResult);
        } catch (Exception e) {
            log.error("query the order detail error {},the orderId is{}", e, orderDTOs.get(0).getOrderId());
        }
        return orderDetailResult;
    }
}
