package com.gdut.pandora.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by buzheng on 18/4/5.
 */
@Configuration
public class Config {

//    /**
//     * 文件上传服务
//     *
//     * @return
//     */
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000000);
//        multipartResolver.setDefaultEncoding("UTF-8");
//        return multipartResolver;
//    }
}
