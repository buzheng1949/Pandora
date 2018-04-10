package com.gdut.pandora.domain.result;

import com.gdut.pandora.domain.User;
import lombok.Data;

import java.util.List;

/**
 * Created by buzheng on 18/4/5.
 */
@Data
public class UserDTO {

    private Integer id;

    private String userName;

    private String password;

    private String address;

    private String userDesc;

    private String email;

    private String phone;

    private Long collection;

    private Long createTime;

    private Long updateTime;

    private String image;
    /**
     * 用户关注列表用户的信息
     */
    private List<User> focus;
}
