package com.gdut.pandora.domain;

import com.alibaba.fastjson.annotation.JSONType;
import com.gdut.pandora.domain.result.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String password;

    /**
     * todo 这里为空返回一个空的类 有点难呀
     */
    private AddressDTO address;

    private String userDesc = "";

    private String collection;

    private String topics;

    private String phone;

    private Long createTime;

    private Long updateTime;

    private String focus = "";

    private String image = "";

}