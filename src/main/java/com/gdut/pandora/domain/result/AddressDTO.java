package com.gdut.pandora.domain.result;

import com.gdut.pandora.domain.Address;
import lombok.Data;

import java.util.Comparator;

/**
 * Created by buzheng on 18/4/13.
 */
@Data
public class AddressDTO {

    private Integer id;

    private Long phone;

    private String address;

    private String uname;

    private Long createTime;

    private Long updateTime;

    private Byte defaultAddress;

}
