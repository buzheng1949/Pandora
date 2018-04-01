package com.gdut.pandora.controller;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by buzheng on 18/3/31.
 */
@RestController
@ResponseBody
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ServerResponse<Boolean> register(UserQuery userQuery) {
        if (userQuery == null) {
            return ServerResponse.createByErrorMessage("请输入具体用户信息");
        }
        try {
            ServerResponse<Boolean> res = userService.registerUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("register the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }


    @RequestMapping("/query")
    public ServerResponse<List<User>> queryUser(UserQuery userQuery) {
        if (userQuery == null || userQuery.getUserName() == null || userQuery.getPassword() ==  null) {
            return ServerResponse.createByErrorMessage("未传入用户名或者密码");
        }
        try {
            ServerResponse<List<User>> res = userService.queryUserMessage(userQuery);
            return res;
        } catch (Exception e) {
            log.error("query the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

    @RequestMapping("/login")
    public ServerResponse<List<User>> login(UserQuery userQuery) {
        if (userQuery == null || userQuery.getUserName() == null || userQuery.getPassword() == null) {
            return ServerResponse.createByErrorMessage("请输入合法的用户名以及密码");
        }
        List<User> userList = userService.queryUserMessage(userQuery).getData();
        if (CollectionUtils.isEmpty(userList)) {
            return ServerResponse.createByErrorMessage("用户名或者密码出错请重试");
        }
        return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc(),userList);
    }


    @RequestMapping("/update")
    public ServerResponse<Boolean> update(UserQuery userQuery) {
        if (userQuery == null || userQuery.getUserName() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID");
        }
        try {
            ServerResponse<Boolean> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("update the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

}
