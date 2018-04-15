package com.gdut.pandora.domain;

import com.gdut.pandora.domain.result.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 订单实体接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;//自己算

    private Long price;//自己算

    private Long uid;//传入

    private Long createTime;//我自己算

    private String image;//根据商品ID算

    private Long num;//根据商品算  总数

    private String tradeItems;//根据商品信息计算

    private String nums;//各个商品的个数

    /**
     * 将订单源数据转换成订单透出数据
     *
     * @param order
     * @return
     */
    public OrderDTO assembleOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setOrderId(order.getId());
        return orderDTO;
    }
}