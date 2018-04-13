package com.gdut.pandora.service;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.UserDTO;

import java.util.List;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口服务
 */
public interface UserService {

    /**
     * 用户注册接口
     *
     * @param userQuery
     * @return
     */
    ServerResponse<Boolean> registerUser(UserQuery userQuery);

    /**
     * 更新用户信息接口
     *
     * @param userQuery
     * @return
     */
    ServerResponse<List<User>> updateUser(UserQuery userQuery);

    /**
     * 查询用户信息接口
     *
     * @param userQuery
     * @return
     */
    ServerResponse<List<User>> queryUserMessage(UserQuery userQuery);

}
