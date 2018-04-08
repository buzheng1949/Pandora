package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(UserQuery userQuery);

    List<User> select(UserQuery userQuery);

    List<User> selectWhthoutPassword(UserQuery userQuery);

    int update(UserQuery userQuery);
}