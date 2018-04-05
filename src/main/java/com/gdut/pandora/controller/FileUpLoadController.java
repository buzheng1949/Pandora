package com.gdut.pandora.controller;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.service.FileService;
import com.gdut.pandora.utils.PropertiesUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by buzheng on 18/4/5.
 */
@RestController
@ResponseBody
@Slf4j
@RequestMapping("/file")
public class FileUpLoadController {

    @Autowired
    private FileService fileService;

    @RequestMapping("upload.do")
    @ResponseBody
    @NeedLogin
    public ServerResponse<String> upload(@RequestParam(value = "upload", required = false) MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);
        String url = new StringBuilder().append(PropertiesUtil.getProperty("ftp.server.http.prefix")).append(targetFileName).toString();
        return ServerResponse.createBySuccessMessage(url);
    }


}
