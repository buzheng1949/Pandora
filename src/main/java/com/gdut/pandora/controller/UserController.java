package com.gdut.pandora.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.*;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.ProductQuery;
import com.gdut.pandora.domain.query.TopicQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.ProductDTO;
import com.gdut.pandora.domain.result.TopicDTO;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.ProductService;
import com.gdut.pandora.service.TopicService;
import com.gdut.pandora.service.UserService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private TopicService topicService;


    @RequestMapping("/collect")
    public ServerResponse<Boolean> collect(@RequestParam(value = "type") Integer type,
                                           @RequestParam(value = "id") Integer id,
                                           @RequestParam(value = "uid") Integer uid,
                                           @RequestParam(value = "collect") Integer collect) {
        if (type == Constant.COLLECTED_TYPE.ITEM) {
            UserQuery userQuery = new UserQuery();
            userQuery.setId(uid);
            List<User> list = userMapper.selectWhthoutPassword(userQuery);
            if (CollectionUtils.isEmpty(list)) {
                log.error("查询用户信息出错,用户is是{}", uid);
                return ServerResponse.createByErrorMessage("用户信息查询出错", Boolean.FALSE);
            }
            User user = list.get(0);
            String[] resultCollection = user.getCollection().split(",");
            List<String> sourceList = new ArrayList<>();
            for (String s : resultCollection) {
                sourceList.add(s);
            }
            Iterator<String> iterator = sourceList.iterator();
            if (collect == 0) {
                while (iterator.hasNext()) {
                    //减少
                    if (id.toString().equals(iterator.next())) {
                        iterator.remove();
                        break;

                    }
                }
            } else {
                if (sourceList.contains(id.toString())) {
                    return ServerResponse.createByErrorMessage("已经收藏该商品，请勿重复收藏", Boolean.FALSE);
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

            userQuery.setCollection(sb.toString());
            int updateRes = userMapper.update(userQuery);
            if (updateRes > 0) {
                return ServerResponse.createBySuccess("success", Boolean.TRUE);
            }
        }
        if (type == Constant.COLLECTED_TYPE.TOPIC) {
            UserQuery userQuery = new UserQuery();
            userQuery.setId(uid);
            List<User> list = userMapper.selectWhthoutPassword(userQuery);
            if (CollectionUtils.isEmpty(list)) {
                log.error("查询用户信息出错,用户is是{}", uid);
                return ServerResponse.createByErrorMessage("用户信息查询出错", Boolean.FALSE);
            }
            User user = list.get(0);
            String[] topics = user.getTopics().split(",");
            List<String> sourceList = new ArrayList<>();
            for (String s : topics) {
                sourceList.add(s);
            }
            Iterator<String> iterator = sourceList.iterator();
            if (collect == 0) {
                while (iterator.hasNext()) {
                    //减少
                    if (id.toString().equals(iterator.next())) {
                        iterator.remove();
                        break;

                    }
                }
            } else {
                if (sourceList.contains(id.toString())) {
                    return ServerResponse.createByErrorMessage("已经收藏该主题，请勿重复收藏", Boolean.FALSE);
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

            userQuery.setTopics(sb.toString());
            int updateRes = userMapper.update(userQuery);
            if (updateRes > 0) {
                return ServerResponse.createBySuccess("success", Boolean.TRUE);
            }
        }
        return ServerResponse.createByErrorMessage("操作出错", Boolean.FALSE);

    }

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
//    @NeedLogin
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public ServerResponse<List<User>> update(@RequestParam(value = "editType", required = true) Integer editType,
                                             @RequestParam(value = "message", required = true) String message,
                                             @RequestParam(value = "phone", required = true) String phone) {
        List<User> userList = new ArrayList<>();
        UserQuery userQuery = new UserQuery();
        try {
            if (editType == EditTypeConstant.IMAGE) {
                userQuery.setImage(message);
            } else if (editType == EditTypeConstant.PASSWORD) {
                userQuery.setPassword(message);
            } else if (editType == EditTypeConstant.USER_DESC) {
                userQuery.setUserDesc(message);
            } else if (editType == EditTypeConstant.USER_NAME) {
                userQuery.setUserName(message);
            } else {
                return ServerResponse.createByErrorMessage("传入非法的修改类型", userList);
            }
            userQuery.setPhone(phone);
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
    public ServerResponse focus(@RequestParam(value = "uid", required = true) Integer id,
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
        for (String s : resultFocus) {
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


    /**
     * 查看用户具体信息的接口 通过phone判断是否是当前用户
     *
     * @param uid
     * @return
     */
    @RequestMapping("/message/query/collect")
    public ServerResponse queryCollect(@RequestParam(value = "uid", required = true) Integer uid) {
        if (uid == null || uid <= 0) {
            return ServerResponse.createByErrorMessage("请传入有效的用户ID", new ArrayList<>());
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setId(uid);
        List<User> self = userMapper.selectWhthoutPassword(userQuery);
        try {
            User user = self.get(0);
            String collections = user.getCollection();
            List<String> collectionList = covertString2List(collections, ",");
            List<Map> collectionResult = new ArrayList<>();
            for (String c : collectionList) {
                ProductQuery p = new ProductQuery();
                p.setId(Integer.valueOf(c));
                List<ProductDTO> productDtos = productService.selectProductList(p);
                if (CollectionUtils.isEmpty(productDtos)) {
                    continue;
                }
                HashMap h = new HashMap();
                ProductDTO one = productDtos.get(0);
                h.put("id", one.getId());
                h.put("image", one.getImage());
                h.put("name", one.getName());
                h.put("title", one.getTitle());
                collectionResult.add(h);
            }
            return ServerResponse.createBySuccess("success", collectionResult);
        } catch (Exception e) {
            log.error("query the user detail error", e);
            return ServerResponse.createByErrorMessage("查询失败", new ArrayList<>());
        }
    }

    /**
     * 查看用户具体信息的接口 通过phone判断是否是当前用户
     *
     * @param uid
     * @return
     */
    @RequestMapping("/message/query/focus")
    public ServerResponse queryFocus(@RequestParam(value = "uid", required = true) Integer uid) {
        if (uid == null || uid <= 0) {
            return ServerResponse.createByErrorMessage("请传入有效的用户ID", new ArrayList<>());
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setId(uid);
        List<User> self = userMapper.selectWhthoutPassword(userQuery);
        try {
            User user = self.get(0);
            String focus = user.getFocus();
            List<String> focusList = covertString2List(focus, ",");
            List<Map> focusResult = new ArrayList<>();
            for (String fs : focusList) {
                UserQuery uQ = new UserQuery();
                uQ.setId(Integer.valueOf(fs));
                List<User> focusSingleUser = userMapper.selectWhthoutPassword(uQ);
                if (CollectionUtils.isEmpty(focusSingleUser)) {
                    continue;
                }
                HashMap h = new HashMap();
                User one = focusSingleUser.get(0);
                h.put("id", one.getId());
                h.put("image", one.getImage());
                h.put("userName", one.getUserName());
                h.put("userDesc", one.getUserDesc());
                focusResult.add(h);
            }
            return ServerResponse.createBySuccess("success", focusResult);
        } catch (Exception e) {
            log.error("query the user detail error", e);
            return ServerResponse.createByErrorMessage("查询失败", new ArrayList<>());
        }


    }

    /**
     * 查看用户具体信息的接口 通过phone判断是否是当前用户
     *
     * @param id
     * @param phone
     * @return
     */
    @RequestMapping("/message/query")
    public ServerResponse queryUserCenter(@RequestParam(value = "uid", required = true) Integer id,
                                          @RequestParam(value = "phone", required = true) String phone) {
        if (id == null || id <= 0) {
            return ServerResponse.createByErrorMessage("请传入有效的用户ID", new ArrayList<>());
        }
        if (phone == null) {
            return ServerResponse.createByErrorMessage("请传入有效的手机号码", new ArrayList<>());
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setId(id);
        List<User> res = userMapper.selectWhthoutPassword(userQuery);
        userQuery.setId(null);
        userQuery.setPhone(phone);
        List<User> self = userMapper.selectWhthoutPassword(userQuery);
        if (CollectionUtils.isEmpty(res) || res.get(0) == null) {
            //当前查询用户不存在
            return ServerResponse.createByErrorMessage("传入的ID用户不存在，请重新检查", new ArrayList<>());
        }
        try {
            User user = res.get(0);
            boolean isSelf = false;
            if (user.getPhone().equals(phone)) {
                isSelf = true;
            }
            JSONObject result = JSONObject.parseObject(JSON.toJSONString(user));
            String focus = user.getFocus();
            String collections = user.getCollection();
            List<String> focusList = covertString2List(focus, ",");
            List<String> collectionList = covertString2List(collections, ",");
            List<String> topics = covertString2List(user.getTopics(), ",");
            List<Map> focusResult = new ArrayList<>();
            User u = self.get(0);
            List<String> selfFocus = covertString2List(u.getFocus(), ",");
            for (String fs : focusList) {
                UserQuery uQ = new UserQuery();
                uQ.setId(Integer.valueOf(fs));
                List<User> focusSingleUser = userMapper.selectWhthoutPassword(uQ);
                if (CollectionUtils.isEmpty(focusSingleUser)) {
                    continue;
                }
                HashMap h = new HashMap();
                User one = focusSingleUser.get(0);
                if (one.getId() == u.getId()){
                    h.put("isFocus",-1);
                }else if (selfFocus.contains(String.valueOf(one.getId()))){
                    h.put("isFocus",1);
                }else{
                    h.put("isFocus",-1);
                }
                h.put("id", one.getId());
                h.put("image", one.getImage());
                h.put("userName", one.getUserName());
                h.put("userDesc", one.getUserDesc());
                focusResult.add(h);
            }
            List<Map> collectionResult = new ArrayList<>();
            for (String c : collectionList) {
                ProductQuery p = new ProductQuery();
                p.setId(Integer.valueOf(c));
                List<ProductDTO> productDtos = productService.selectProductList(p);
                if (CollectionUtils.isEmpty(productDtos)) {
                    continue;
                }
                HashMap h = new HashMap();
                ProductDTO one = productDtos.get(0);
                h.put("id", one.getId());
                h.put("image", one.getImage());
                h.put("name", one.getName());
                h.put("title", one.getTitle());
                collectionResult.add(h);
            }
            List<TopicDTO> topicMap = new ArrayList<>();
            for (String c : topics) {
                TopicQuery p = new TopicQuery();
                p.setId(Integer.valueOf(c));
                List<TopicDTO> topicDTOs = topicService.listTopic(p);
                if (CollectionUtils.isEmpty(topicDTOs)) {
                    continue;
                }
                HashMap h = new HashMap();
                TopicDTO one = topicDTOs.get(0);
                topicMap.add(one);
            }
            result.put("topics", topicMap);
            result.put("focus", focusResult);
            result.put("collection", collectionResult);
            result.remove("password");
            if (isSelf) {
                result.put("isFocus", -1);
            } else {
                if (self != null && self.size() > 0) {
                    if (selfFocus.contains(String.valueOf(id))) {
                        result.put("isFocus", 1);
                    } else {
                        result.put("isFocus", 0);
                    }
                } else {
                    result.put("isFocus", -1);
                }

            }
            return ServerResponse.createBySuccess("success", result);
        } catch (Exception e) {
            log.error("query the user detail error", e);
            return ServerResponse.createByErrorMessage("查询失败", new ArrayList<>());
        }


    }

    public List<String> covertString2List(String s, String split) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(s) && s.split(split) == null) {
            return result;
        }
        String[] resultStr = s.split(split);
        for (String so : resultStr) {
            result.add(so);
        }
        return result;
    }


}
