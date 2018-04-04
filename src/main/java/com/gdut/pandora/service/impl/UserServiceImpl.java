package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.TimeUtils;
import com.gdut.pandora.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<Boolean> registerUser(UserQuery userQuery) {
        boolean result = false;
        if (!UserUtils.isValidUser(userQuery)) {
            return ServerResponse.createByErrorMessage("用户信息不全，请检查用户信息");
        }
        userQuery.setCreateTime(TimeUtils.getCurrentTime());
        userQuery.setUpdateTime(TimeUtils.getCurrentTime());
        ServerResponse<List<User>> userList = queryUserMessage(userQuery);
        if (userList.getStatus() == ResponseCode.ERROR.getCode() || CollectionUtils.isNotEmpty(userList.getData())) {
            ServerResponse.createByErrorMessage("用户已经存在，请直接登陆");
        }
        Integer res = userMapper.insert(userQuery);
        if (res > 0) {
            result = true;
            return ServerResponse.createBySuccess("用户注册成功", result);
        }
        return ServerResponse.createBySuccess("用户注册失败，服务端出错了", result);
    }

    @Override
    public ServerResponse<Boolean> updateUser(UserQuery userQuery) {
        boolean result = false;
        if (userQuery == null || StringUtils.isEmpty(userQuery.getPhone())) {
            return ServerResponse.createByError();
        }
        userQuery.setUpdateTime(TimeUtils.getCurrentTime());
        int res = userMapper.update(userQuery);
        if (res > 0) {
            result = true;
            return ServerResponse.createBySuccess("用户更新信息成功", result);
        }
        return ServerResponse.createBySuccess("用户更新信息失败", result);
    }

    @Override
    public ServerResponse<List<User>> queryUserMessage(UserQuery userQuery) {
        if (userQuery == null) {
            return ServerResponse.createByError();
        }
        if (StringUtils.isEmpty(userQuery.getPhone()) || StringUtils.isEmpty(userQuery.getPassword())) {
            return ServerResponse.createByErrorMessage("请输入用户名以及密码");
        }
        List<User> res = userMapper.select(userQuery);
        return ServerResponse.createBySuccess("success", res);
    }


}
