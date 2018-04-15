package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderListDataResult;
import com.gdut.pandora.service.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 */
@Slf4j
@Service
public class OrderListServiceImpl implements OrderListService {

    @Override
    public List<OrderListDataResult> assembleOrderListDataList(List<OrderDTO> orderDTOList) {
        List<OrderListDataResult> orderListDataResults = new ArrayList<>();
        try {
            if (CollectionUtils.isEmpty(orderDTOList)) {
                return orderListDataResults;
            }
            for (OrderDTO orderDTO : orderDTOList) {
                OrderListDataResult orderListDataResult = new OrderListDataResult();
                orderListDataResult.setOrderId(orderDTO.getOrderId());
                orderListDataResult.setCreateTime(orderDTO.getCreateTime());
                orderListDataResult.setPrice(orderDTO.getPrice());
                String sourceImages = orderDTO.getImage();
                String[] resultImages = sourceImages.split(",");
                orderListDataResult.setImage(resultImages);
                orderListDataResults.add(orderListDataResult);
            }
        } catch (Exception e) {
            log.error("the result is error {},the oerderId is {}", e);
        }
        return orderListDataResults;
    }
}
