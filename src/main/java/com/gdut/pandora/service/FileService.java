package com.gdut.pandora.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by buzheng on 18/4/5.
 * 文件上传服务类
 */
public interface FileService {

    /**
     * 文件上传服务
     *
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);

}
