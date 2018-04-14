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
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/register")
    public ServerResponse<Boolean> register(UserQuery userQuery) {
        if (userQuery == null) {
            return ServerResponse.createByErrorMessage("请输入具体用户信息", false);
        }
        try {
            ServerResponse<Boolean> res = userService.registerUser(userQuery);
            ServerResponse<List<User>> response = userService.queryUserMessage(userQuery, true);
            if (response.getStatus() == 1) {
                User user = response.getData().get(0);
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                session.setAttribute(user.getPhone(), user);
            }
            return res;
        } catch (Exception e) {
            log.error("register the user error", e);
            return ServerResponse.createByErrorMessage("注册失败，请重新试试~~", false);
        }
    }


    @RequestMapping("/query")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> queryUser(UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户的手机号以及密码", users);
        }
        try {
            ServerResponse<List<User>> res = userService.queryUserMessage(userQuery, true);
//            if (!CollectionUtils.isEmpty(res.getData())) {
//                session.setAttribute(Constant.SESSION.CURRENT_USER, res.getData().get(0));
//            }
            return res;
        } catch (Exception e) {
            log.error("query the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错", users);
    }

    @RequestMapping("/login")
    public ServerResponse<List<User>> login(UserQuery userQuery) {
        List<User> userList = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null || userQuery.getPassword() == null) {
            return ServerResponse.createByErrorMessage("请输入合法的用户名以及密码", userList);
        }
        userList = userService.queryUserMessage(userQuery, false).getData();
        if (CollectionUtils.isEmpty(userList)) {
            return ServerResponse.createByErrorMessage("用户名或者密码出错请重试", userList);
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
        return ServerResponse.createBySuccess("退出成功", result);
    }


    @RequestMapping("/update")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> update(UserQuery userQuery) {
        List<User> userList = new ArrayList<>();
        if (userQuery == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID", userList);
        }
        try {
            ServerResponse<List<User>> res = userService.updateUser(userQuery);
            return res;
        } catch (Exception e) {
            log.error("update the user error", e);
        }
        return ServerResponse.createByErrorMessage("服务端处理出错", userList);
    }

    /**
     * 进行用户取消与否操作
     *
     * @param id
     * @param isFocus
     * @param phone
     * @return
     */
    @RequestMapping("/message/update")
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse queryUserCenter(@RequestParam(value = "id", required = true) Integer id,
                                          @RequestParam(value = "isFocus", required = true) Integer isFocus,
                                          @RequestParam(value = "phone", required = true) String phone) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("请传入需要移除用户的ID", new ArrayList<>());
        }
        if (isFocus == null) {
            return ServerResponse.createByErrorMessage("请传入是否关注或者取消关注用户动作", new ArrayList<>());
        }

        if (isFocus != 1 && isFocus != 0) {
            return ServerResponse.createByErrorMessage("请传入关注或者取消关注的正确信息", new ArrayList<>());
        }
        if (phone == null) {
            return ServerResponse.createByErrorMessage("请传入当前登录用户的手机号", new ArrayList<>());
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setPhone(phone);
        List<User> res = userMapper.selectWhthoutPassword(userQuery);
        if (CollectionUtils.isEmpty(res) || res.get(0) == null) {
            //当前查询用户不存在
            return ServerResponse.createByErrorMessage("当前登录用户信息不正确", new ArrayList<>());
        }
        User user = res.get(0);
        String[] resultFocus = user.getFocus().split(",");
        List<String> sourceList = new ArrayList<>();
        for (String s : resultFocus){
            sourceList.add(s);
        }
        Iterator<String> iterator = sourceList.iterator();
        if (isFocus == 1) {
            while (iterator.hasNext()) {
                //减少
                if (id.toString().equals(iterator.next())) {
                    iterator.remove();
                    break;

                }
            }
        } else {
            if (sourceList.contains(id.toString())) {
                return ServerResponse.createByErrorMessage("已经关注该用户，请勿重新关注", new ArrayList<>());
            } else {
                sourceList.add(0, id.toString());
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(sourceList)) {
            for (int i = 0; i < sourceList.size(); i++) {
                if (i != sourceList.size() - 1) {
                    sb.append(sourceList.get(i)).append(",");
                } else {
                    sb.append(sourceList.get(i));
                }
            }
        }

        userQuery.setFocus(sb.toString());
        int updateRes = userMapper.update(userQuery);
        HashMap hash = new HashMap();
        //试试这种吧
        if (updateRes >= 0) {
            hash.put("status", 1);
            hash.put("message", "更新成功");
            HashMap data = new HashMap();
            data.put("id", id);
            data.put("isFocus", isFocus);
            hash.put("data", data);
            return ServerResponse.createBySuccess("更新成功", hash);
        } else {
            hash.put("status", 0);
            hash.put("message", "更新失败");
            hash.put("data", new ArrayList<>());
            return ServerResponse.createBySuccess("更新成功", hash);
        }


    }

    @RequestMapping("/focus")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> focus(HttpSession session, UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入者用户手机号码", users);
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
        return ServerResponse.createByErrorMessage("服务端处理出错", users);
    }

    @RequestMapping("/removefocus")
    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> remove(HttpSession session, UserQuery userQuery) {
        List<User> users = new ArrayList<>();
        if (userQuery == null || userQuery.getId() == null || userQuery.getPhone() == null) {
            return ServerResponse.createByErrorMessage("未传入用户ID或者用户手机号码", users);
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
        return ServerResponse.createByErrorMessage("服务端处理出错", users);
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
