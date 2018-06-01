package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Product;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.ProductService;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.TimeUtils;
import com.gdut.pandora.utils.UserUtils;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * Created by buzheng on 18/3/31.
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserUtils userUtils;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String DEFAULT_AVATAR = "https://s3.mogucdn.com/mlcdn/c024f5/180413_8a59h26ka3dkfdbi6698cg8j409kd_651x656.png";

    @Override
    public ServerResponse<Boolean> registerUser(UserQuery userQuery) {
        boolean result = false;
        if (!UserUtils.isValidUser(userQuery)) {
            return ServerResponse.createByErrorMessage("用户信息不全，请检查用户信息",false);
        }
        if (StringUtils.isEmpty(userQuery.getUserName())) {
            userQuery.setUserName("潘多拉的魔法家");
        }
        if (StringUtils.isEmpty(userQuery.getImage())) {
            userQuery.setImage(DEFAULT_AVATAR);
        }
        userQuery.setFocus("0");
        userQuery.setCollection("0");
        userQuery.setCreateTime(TimeUtils.getCurrentTime());
        userQuery.setUpdateTime(TimeUtils.getCurrentTime());
        ServerResponse<List<User>> userList = queryUserMessage(userQuery,false);
        if (userList.getData() != null && userList.getData().size() > 0) {
            logger.error("重复用户 the usermessage is "+JSON.toJSONString(userQuery));
            return ServerResponse.createByErrorMessage("用户已经存在，请直接登陆",false);
        }
        Integer res = userMapper.insert(userQuery);
        if (res > 0) {
            result = true;
            return ServerResponse.createBySuccess("用户注册成功", result);
        }
        return ServerResponse.createByErrorMessage("用户注册失败",false);
    }

    @Override
    public ServerResponse<List<User>> updateUser(UserQuery userQuery) {
        List<User> result  = new ArrayList<>();
        if (userQuery == null || StringUtils.isEmpty(userQuery.getPhone())) {
            return ServerResponse.createByErrorMessage("更新用户信息时请带上手机号码",result);
        }
        userQuery.setUpdateTime(TimeUtils.getCurrentTime());
        int res = userMapper.update(userQuery);
        if (res > 0) {
            result = userMapper.selectWhthoutPassword(userQuery);
            if(!CollectionUtils.isEmpty(result)){
                User user = result.get(0);
                String[] focusUserList = user.getFocus().split(",");
                String[] collectionItems = user.getCollection().split(",");
                user.setFocus(String.valueOf(focusUserList.length));
                user.setCollection(String.valueOf(collectionItems.length));
                userUtils.addAddressMessageToUser(user);
            }
            return ServerResponse.createBySuccess("success", result);
        }
        return ServerResponse.createByErrorMessage("用户更新信息失败",result);
    }

    @Override
    public ServerResponse<List<User>> queryUserMessage(UserQuery userQuery,boolean login) {
        List<User> res = new ArrayList<>();
        if (userQuery == null) {
            return ServerResponse.createByErrorMessage("请传入有效查询参数",res);
        }
        if (StringUtils.isEmpty(userQuery.getPhone())) {
            return ServerResponse.createByErrorMessage("请输入用户手机号码",res);
        }
        if(login){
            res = userMapper.selectWhthoutPassword(userQuery);
        }else{
            res = userMapper.select(userQuery);
        }
        if(!CollectionUtils.isEmpty(res)){
            User user = res.get(0);
            String[] focusUserList = user.getFocus().split(",");
            String[] collectionItems = user.getCollection().split(",");
            user.setFocus(String.valueOf(focusUserList.length));
            user.setCollection(String.valueOf(collectionItems.length));
            userUtils.addAddressMessageToUser(user);
        }
//        List<UserDTO> targetList = assembleUserResult(res);
        if (!CollectionUtils.isEmpty(res)) {
            return ServerResponse.createBySuccess("success", res);
        } else {
            return ServerResponse.createByErrorMessage("当前用户不存在",res);
        }
    }




}
