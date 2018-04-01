package com.gdut.pandora.controller;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ServerResponse<Boolean> register(User user) {
        if (user == null) {
            return ServerResponse.createByErrorMessage("请输入具体用户信息");
        }
        try {
            ServerResponse<Boolean> res = userService.registerUser(user);
            return res;
        } catch (Exception e) {
            log.error("register the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

    @RequestMapping("/query")
    public ServerResponse<User> queryUser(User user) {
        if (user == null || user.getId() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID");
        }
        try {
            ServerResponse<User> res = userService.queryUserMessage(user);
            return res;
        } catch (Exception e) {
            log.error("query the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }


    @RequestMapping("/update")
    public ServerResponse<Boolean> update(User user) {
        if (user == null || user.getId() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID");
        }
        try {
            ServerResponse<Boolean> res = userService.updateUser(user);
            return res;
        } catch (Exception e) {
            log.error("update the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

}
