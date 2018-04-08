package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.TimeUtils;
import com.gdut.pandora.utils.UserUtils;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        if (StringUtils.isEmpty(userQuery.getUserName())) {
            userQuery.setUserName("潘多拉的魔法家");
        }
        userQuery.setCreateTime(TimeUtils.getCurrentTime());
        userQuery.setUpdateTime(TimeUtils.getCurrentTime());
        ServerResponse<List<UserDTO>> userList = queryUserMessage(userQuery);
        if (userList.getData() != null && userList.getData().size() > 0) {
            return ServerResponse.createByErrorMessage("用户已经存在，请直接登陆");
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
    public ServerResponse<List<UserDTO>> queryUserMessage(UserQuery userQuery) {
        if (userQuery == null) {
            return ServerResponse.createByError();
        }
        if (StringUtils.isEmpty(userQuery.getPhone())) {
            return ServerResponse.createByErrorMessage("请输入用户手机号码");
        }
        List<User> res = userMapper.selectWhthoutPassword(userQuery);
        List<UserDTO> targetList = assembleUserResult(res);
        return ServerResponse.createBySuccess("success", targetList);
    }

    /**
     * 查询用户关注的用户并进行返回
     *
     * @param sourceUserList
     * @return
     */
    private List<UserDTO> assembleUserResult(List<User> sourceUserList) {
        List<UserDTO> targetUserDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourceUserList)) {
            for (User user : sourceUserList) {
                List<User> users = new ArrayList<>();
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(user, userDTO);
                if (!StringUtils.isEmpty(user.getFocus())) {
                    String[] focusUserList = user.getFocus().split(",");
                    for (String id : focusUserList) {
                        UserQuery query = new UserQuery();
                        query.setId(Integer.valueOf(id));
                        List<User> list = userMapper.select(query);
                        users.addAll(list);
                    }
                    userDTO.setFocus(users);
                }
                targetUserDTOList.add(userDTO);
            }
        }
        return targetUserDTOList;
    }


}
