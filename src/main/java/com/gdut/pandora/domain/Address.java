package com.gdut.pandora.domain;

import lombok.Data;

import java.util.Comparator;

@Data
public class Address {
    private Integer id;

    private Long phone;

    private Long uid;

    private String uname;

    private String address;

    private Long createTime;

    private Long updateTime;

    private Byte defaultAddress;

}