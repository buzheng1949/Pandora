package com.gdut.pandora.controller;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.query.TopicQuery;
import com.gdut.pandora.domain.result.TopicDTO;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.service.FileService;
import com.gdut.pandora.service.TopicService;
import com.gdut.pandora.utils.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ResponseBody
    @RequestMapping("/list")
    public ServerResponse<List<TopicDTO>> listTopics(TopicQuery topicQuery) {
        List<TopicDTO> list = topicService.listTopic(topicQuery);
        return ServerResponse.createBySuccessMessage(list);
    }

    @ResponseBody
    @RequestMapping("/public")
    @NeedLogin
    public ServerResponse<Boolean> publicTopic(HttpSession session, TopicQuery topicQuery, @RequestParam(value = "upload", required = false) MultipartFile file, HttpServletRequest request) {
        boolean res = false;
        try {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);
            String url = new StringBuilder().append(PropertiesUtil.getProperty("ftp.server.http.prefix")).append(targetFileName).toString();
            UserDTO userDTO = (UserDTO) session.getAttribute(Constant.SESSION.CURRENT_USER);
            topicQuery.setUserId(userDTO.getId());
            topicQuery.setUserImage(userDTO.getImage());
            topicQuery.setUserName(userDTO.getUserName());
            topicQuery.setTopicImage(url);
            res = topicService.publicTopic(topicQuery);
        } catch (Exception e) {
            log.error("public topic error", e);
            return ServerResponse.createByErrorMessage("发布失败，请检查传入参数是否齐全");
        }
        return ServerResponse.createBySuccessMessage(res);
    }

}
