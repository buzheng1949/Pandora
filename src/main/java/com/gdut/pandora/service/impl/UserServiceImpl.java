package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public ServerResponse<Boolean> registerUser(User user) {
        return null;
    }

    @Override
    public ServerResponse<Boolean> updateUser(User user) {
        return null;
    }

    @Override
    public ServerResponse<User> queryUserMessage(User user) {
        return null;
    }
}
