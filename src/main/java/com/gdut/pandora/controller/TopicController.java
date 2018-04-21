package com.gdut.pandora.controller;

import com.alibaba.fastjson.JSON;
import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ReturnTypeEnum;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.TopicQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.TopicDTO;
import com.gdut.pandora.mapper.UserMapper;
import com.gdut.pandora.service.FileService;
import com.gdut.pandora.service.TopicService;
import com.gdut.pandora.service.UserService;
import com.gdut.pandora.utils.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by buzheng on 18/4/6.
 */
@RequestMapping("/topic")
@ResponseBody
@RestController
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/list")
    public ServerResponse<List<TopicDTO>> listTopics(TopicQuery topicQuery) {
        List<TopicDTO> list = topicService.listTopic(topicQuery);
        return ServerResponse.createBySuccessMessage(list);
    }

    @ResponseBody
    @RequestMapping("/public")
    @ReturnType(type = ReturnTypeEnum.BOOLEAN_TYPE)
    public ServerResponse<Boolean> publicTopic(TopicQuery topicQuery) {
        boolean res = false;
        try {
            if (topicQuery == null || topicQuery.getUserId() == null || topicQuery.getHeight() == null || topicQuery.getWidth() == null || topicQuery.getShopName() == null) {
                log.error("专题发布失败请求参数是{}", JSON.toJSONString(topicQuery));
                return ServerResponse.createByErrorMessage("发布失败，请检查传入参数是否齐全", Boolean.FALSE);
            }
            if (topicQuery.getLikeNum() == null || topicQuery.getLikeNum() < 0 || topicQuery.getLikeNum() > 5) {
                log.error("专题发布失败请求参数是{}", JSON.toJSONString(topicQuery));
                return ServerResponse.createByErrorMessage("请输入合法的评分", Boolean.FALSE);
            }

            UserQuery userQuery = new UserQuery();
            userQuery.setId(topicQuery.getUserId());
            List<User> users = userMapper.selectWhthoutPassword(userQuery);
            if (CollectionUtils.isEmpty(users)) {
                log.error("专题发布失败，不存在该用户,请求参数是{}", JSON.toJSONString(topicQuery));
                return ServerResponse.createByErrorMessage("发布失败，请检查传入参数是否齐全", Boolean.FALSE);
            }
            User user = users.get(0);
            topicQuery.setUserId(user.getId());
            topicQuery.setUserImage(user.getImage());
            topicQuery.setUserName(user.getUserName());
            res = topicService.publicTopic(topicQuery);
        } catch (Exception e) {
            log.error("public topic error", e);
            return ServerResponse.createByErrorMessage("发布失败，请检查传入参数是否齐全", Boolean.FALSE);
        }
        return ServerResponse.createBySuccessMessage(res);
    }

}
