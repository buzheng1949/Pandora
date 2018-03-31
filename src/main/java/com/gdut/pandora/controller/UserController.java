package com.gdut.pandora.controller;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ServerResponse<Boolean> register(User user) {
        if (user == null) {
            return ServerResponse.createByErrorMessage("请输入具体用户信息");
        }
        return userService.registerUser(user);
    }

    @RequestMapping("/query")
    public ServerResponse<User> queryUser(User user) {
        if (user == null || user.getId() == null ) {
            return ServerResponse.createByErrorMessage("未传入用户ID");
        }
        return userService.queryUserMessage(user);
    }


    @RequestMapping("/update")
    public ServerResponse<Boolean> update(User user) {
        if (user == null || user.getId() == null ) {
            return ServerResponse.createByErrorMessage("未传入用户ID");
        }
        return userService.updateUser(user);
    }

}
