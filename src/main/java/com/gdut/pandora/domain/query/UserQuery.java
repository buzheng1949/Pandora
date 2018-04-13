package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/1.
 */
@Data
public class UserQuery extends BaseQuery {

    private Integer id;

    private String userName;

    private String password;

    private String address;

    private String userDesc;

    private String email;

    private String collection;

    private String phone;

    private Long createTime;

    private Long updateTime;

    private String image;

    private String focus;
}
