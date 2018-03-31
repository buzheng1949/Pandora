package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.service.UserService;
import org.apache.tomcat.jni.Time;
import org.joda.time.DateTimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by buzheng on 18/3/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void registerUser() throws Exception {
        User user = new User();
        user.setEmail("yupeibiao@gamil.com");
        user.setPassword("test123");
        user.setPhone("17766666");
        String name = new String("不正");
        System.out.print(name.getBytes());
        user.setUserName(name);
        user.setCreateTime(DateTimeUtils.currentTimeMillis()/1000);
        user.setUpdateTime(DateTimeUtils.currentTimeMillis()/1000);
        ServerResponse<Boolean> response = userService.registerUser(user);
        Assert.assertEquals(response.getData(), true);
    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void queryUserMessage() throws Exception {

    }

}