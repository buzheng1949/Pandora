package com.gdut.pandora.service.impl;

import com.gdut.pandora.service.FileService;
import com.gdut.pandora.utils.FTPUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by buzheng on 18/4/5.
 */
@Service
public class FileServiceImpl implements FileService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    public String upload(MultipartFile file, String path) {
        System.out.println("《《《《《《《" + file);
        String fileName = file.getOriginalFilename();
        System.out.println("《《《《《《《" + fileName);
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = new StringBuilder().append(UUID.randomUUID().toString()).append(".").append(fileExtensionName).toString();

        logger.info("开始上传的文件...  上传的文件名:{},上传的路径:{},上传的新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();//创建路径下所有的文件夹
        }
        File targetFile = new File(path + "/" + uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
