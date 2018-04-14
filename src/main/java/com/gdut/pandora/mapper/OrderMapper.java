package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Order;
import com.gdut.pandora.domain.query.OrderQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户订单mapper
 */
@Mapper
public interface OrderMapper {
    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入订单
     *
     * @param record
     * @return
     */
    int insert(Order record);

    /**
     * 查询订单
     *
     * @param orderQuery
     * @return
     */
    List<Order> select(OrderQuery orderQuery);

}