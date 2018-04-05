package com.gdut.pandora.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by buzheng on 18/4/5.
 */
@Slf4j
public class FTPUtil {


    @Getter
    @Setter
    private String ip;
    @Getter
    @Setter
    private int port;
    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private String pwd;
    @Getter
    @Setter
    private FTPClient ftpClient;


    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPassword = PropertiesUtil.getProperty("ftp.pass");
    private static int ftpPort = 21;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }


    public static boolean uploadFile(List<File> list) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpUser, ftpPassword);
        log.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile("img", list);
        log.info("结束上传，上传结果{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> list) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                for (File fileItem : list) {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                log.error("转换工作目录异常...", e);
                uploaded = false;
            } finally {
                ftpClient.disconnect();
                fis.close();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {
        System.out.println("ip:" + ip + " user:" + user + " pwd:" + pwd);
        Boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            log.error("FTP连接失败...", e);
        }

        return isSuccess;
    }

}
