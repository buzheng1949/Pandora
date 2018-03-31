package com.gdut.pandora.service;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口服务
 */
public interface UserService {

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    ServerResponse<Boolean> registerUser(User user);

    /**
     * 更新用户信息接口
     *
     * @param user
     * @return
     */
    ServerResponse<Boolean> updateUser(User user);

    /**
     * 查询用户信息接口
     *
     * @param user
     * @return
     */
    ServerResponse<User> queryUserMessage(User user);

}
