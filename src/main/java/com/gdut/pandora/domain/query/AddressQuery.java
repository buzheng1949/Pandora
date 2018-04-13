package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/13.
 */
@Data
public class AddressQuery {
    private Integer id;

    private Long phone;

    private Long userId;

    private String address;

    private Long createTime;

    private Long updateTime;

    private Byte defaultAddress;
}
