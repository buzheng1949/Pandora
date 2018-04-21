package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Cart;
import com.gdut.pandora.domain.query.CartQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    /**
     * 删除某个购物车记录
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 加入商品到购物车
     *
     * @param record
     * @return
     */
    int insert(Cart record);

    /**
     * 查询购物车列表
     *
     * @param cartQuery
     * @return
     */
    List<Cart> select(CartQuery cartQuery);


    /**
     * 更新购物车
     *
     * @param cartQuery
     * @return
     */
    int update(CartQuery cartQuery);
}