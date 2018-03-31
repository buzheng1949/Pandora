package com.gdut.pandora.domain;

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

    private String email;

    private String phone;

    private Long createTime;

    private Long updateTime;

}