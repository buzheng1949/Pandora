package com.gdut.pandora.controller;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ResponseCode;
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

import javax.servlet.http.HttpSession;
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
    public ServerResponse<List<UserDTO>> queryUser(HttpSession session, UserQuery userQuery) {
        if (userQuery == null || userQuery.getUserName() == null || userQuery.getPassword() == null) {
            return ServerResponse.createByErrorMessage("未传入用户名或者密码");
        }
        try {
            ServerResponse<List<UserDTO>> res = userService.queryUserMessage(userQuery);
            if (!CollectionUtils.isEmpty(res.getData())) {
                session.setAttribute(Constant.SESSION.CURRENT_USER, res.getData().get(0));
            }
            return res;
        } catch (Exception e) {
            log.error("query the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

    @RequestMapping("/login")
    public ServerResponse<List<UserDTO>> login(HttpSession session, UserQuery userQuery) {
        if (userQuery == null || userQuery.getPhone() == null || userQuery.getPassword() == null) {
            return ServerResponse.createByErrorMessage("请输入合法的用户名以及密码");
        }
        List<UserDTO> userList = userService.queryUserMessage(userQuery).getData();
        if (CollectionUtils.isEmpty(userList)) {
            return ServerResponse.createByErrorMessage("用户名或者密码出错请重试");
        }

        //只有一个用户 获取用户并且放入session里面
        UserDTO user = userList.get(0);
        session.setAttribute(Constant.SESSION.CURRENT_USER, user);
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
        session.removeAttribute(Constant.SESSION.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("退出成功");
    }


    @RequestMapping("/update")
    @NeedLogin
    public ServerResponse<Boolean> update(UserQuery userQuery) {
        if (userQuery == null) {
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

    @RequestMapping("/focus")
    @NeedLogin
    public ServerResponse<Boolean> focus(HttpSession session, UserQuery userQuery) {
        if (userQuery == null || userQuery.getId() == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID或者用户手机号码");
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
            realQuery.setId(((UserDTO) session.getAttribute(Constant.SESSION.CURRENT_USER)).getId());
            ServerResponse<Boolean> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("增加用户关注处理逻辑错误", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

    @RequestMapping("/removefocus")
    @NeedLogin
    public ServerResponse<Boolean> remove(HttpSession session, UserQuery userQuery) {
        if (userQuery == null || userQuery.getId() == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID或者用户手机号码");
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
            realQuery.setId(((UserDTO) session.getAttribute(Constant.SESSION.CURRENT_USER)).getId());
            ServerResponse<Boolean> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("增加用户关注处理逻辑错误", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错");
    }

    /**
     * 再次转化
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
