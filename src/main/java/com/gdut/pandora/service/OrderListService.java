package com.gdut.pandora.service;

import com.gdut.pandora.domain.result.OrderDTO;
import com.gdut.pandora.domain.result.OrderListDataResult;

import java.util.List;

/**
 * Created by buzheng on 18/4/15.
 * 订单列表真实透出角度
 */
public interface OrderListService {

    /**
     * 从dto转成对外透出需要的实体类
     *
     * @param orderDTOList
     * @return
     */
    List<OrderListDataResult> assembleOrderListDataList(List<OrderDTO> orderDTOList);


}
