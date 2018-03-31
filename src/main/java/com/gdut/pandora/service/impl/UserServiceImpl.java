package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<Boolean> registerUser(User user) {
        boolean result = false;
        if (!UserUtils.isValidUser(user)) {
            return ServerResponse.createByErrorMessage("用户信息不全，请检查用户信息");
        }
        Integer res = userMapper.insert(user);
        if (res > 0) {
            result = true;
            return ServerResponse.createBySuccess("用户注册成功", result);
        }
        return ServerResponse.createBySuccess("用户注册失败，服务端出错了", result);
    }

    @Override
    public ServerResponse<Boolean> updateUser(User user) {
        boolean result = false;
        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            return ServerResponse.createByError();
        }
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res > 0) {
            result = true;
            return ServerResponse.createBySuccess("用户注册成功", result);
        }
        return ServerResponse.createBySuccess("用户更新信息失败", result);
    }

    @Override
    public ServerResponse<User> queryUserMessage(User user) {
        if (user == null || user.getId() == null) {
            return ServerResponse.createByError();
        }
        User res = userMapper.selectByPrimaryKey(user.getId());
        return ServerResponse.createBySuccessMessage(user);
    }
}
