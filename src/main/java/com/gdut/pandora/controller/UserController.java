package com.gdut.pandora.controller;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ReturnTypeEnum;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
            return ServerResponse.createByErrorMessage("请输入具体用户信息",false);
        }
        try {
            ServerResponse<Boolean> res = userService.registerUser(userQuery);
            ServerResponse<List<User>> response = userService.queryUserMessage(userQuery,true);
            if(response.getStatus() == 1){
                User user = response.getData().get(0);
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                session.setAttribute(user.getPhone(), user);
            }
            return res;
        } catch (Exception e) {
            log.error("register the user error", e);
            return ServerResponse.createByErrorMessage("注册失败，请重新试试~~",false);
        }
    }


    @RequestMapping("/query")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> queryUser(UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户的手机号以及密码",users);
        }
        try {
            ServerResponse<List<User>> res = userService.queryUserMessage(userQuery,true);
//            if (!CollectionUtils.isEmpty(res.getData())) {
//                session.setAttribute(Constant.SESSION.CURRENT_USER, res.getData().get(0));
//            }
            return res;
        } catch (Exception e) {
            log.error("query the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错",users);
    }

    @RequestMapping("/login")
    public ServerResponse<List<User>> login(UserQuery userQuery) {
        List<User> userList = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null || userQuery.getPassword() == null) {
            return ServerResponse.createByErrorMessage("请输入合法的用户名以及密码",userList);
        }
        userList = userService.queryUserMessage(userQuery,false).getData();
        if (CollectionUtils.isEmpty(userList)) {
            return ServerResponse.createByErrorMessage("用户名或者密码出错请重试",userList);
        }

        //只有一个用户 获取用户并且放入session里面
        User user = userList.get(0);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(user.getPhone(), user);
        return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc(), userList);
    }

    /**
     * 用户登出操作
     *
     * @param session
     * @param userQuery
     * @return
     */
    @RequestMapping("/logout")
    public ServerResponse<List<User>> logout(HttpSession session, UserQuery userQuery) {
        List<User> result = new ArrayList<>();
        session.removeAttribute(userQuery.getPhone());
        return ServerResponse.createBySuccess("退出成功",result);
    }


    @RequestMapping("/update")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> update(UserQuery userQuery) {
        List<User> userList = new ArrayList<>();
        if (userQuery == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID",userList);
        }
        try {
            ServerResponse<List<User>> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("update the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错",userList);
    }

    @RequestMapping("/focus")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> focus(HttpSession session, UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入者用户手机号码",users);
        }
        try {
            UserDTO userDTO = (UserDTO) session.getAttribute(Constant.SESSION.CURRENT_USER);
            List<User> focusList = userDTO.getFocus();
            StringBuilder sb = new StringBuilder(userQuery.getId()).append(",");
            if (!CollectionUtils.isEmpty(focusList)) {
                rebuildFocusStr(focusList, sb);
            }
            UserQuery realQuery = new UserQuery();
            realQuery.setFocus(sb.toString());
            realQuery.setId(((UserDTO) session.getAttribute(userQuery.getPhone())).getId());
            ServerResponse<List<User>> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("增加用户关注处理逻辑错误", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错",users);
    }

    @RequestMapping("/removefocus")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> remove(HttpSession session, UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getId() == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID或者用户手机号码",users);
        }
        try {
            UserDTO userDTO = (UserDTO) session.getAttribute(Constant.SESSION.CURRENT_USER);
            List<User> focusList = userDTO.getFocus();
            StringBuilder sb = new StringBuilder(userQuery.getId()).append(",");
            if (!CollectionUtils.isEmpty(focusList)) {
                if (focusList.indexOf(userQuery.getId()) != -1) {
                    focusList.remove(userQuery.getId());
                }
                rebuildFocusStr(focusList, sb);
            }
            UserQuery realQuery = new UserQuery();
            realQuery.setFocus(sb.toString());
            realQuery.setId(((UserDTO) session.getAttribute(userQuery.getPhone())).getId());
            ServerResponse<List<User>> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("增加用户关注处理逻辑错误", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错",users);
    }

    /**
     * 再次转化
     *
     * @param focusList
     * @param sb
     */
    private void rebuildFocusStr(List<User> focusList, StringBuilder sb) {
        for (int i = 0; i < focusList.size(); i++) {
            //遇到的是时候先转吧
            User u = (User) focusList.get(i);
            if (i != focusList.size() - 1) {
                sb.append(u.getId()).append(",");
            } else {
                sb.append(u.getId());
            }
        }
    }

}
