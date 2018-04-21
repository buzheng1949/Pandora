package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Shop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper {

    Shop selectByPrimaryKey(Integer id);
}