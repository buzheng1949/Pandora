package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}