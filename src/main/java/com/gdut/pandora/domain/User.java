package com.gdut.pandora.domain;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String password;

    private String address;

    private String userDesc;

    private String collection;

    private String email;

    private String phone;

    private Long createTime;

    private Long updateTime;

    private String focus;

    private String image;

}