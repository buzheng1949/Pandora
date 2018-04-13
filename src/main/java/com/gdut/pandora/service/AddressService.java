package com.gdut.pandora.service;

import com.gdut.pandora.domain.Address;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.result.AddressDTO;

import java.util.List;

/**
 * Created by buzheng on 18/4/13.
 */
public interface AddressService {

    /**
     * 获取地址列表
     * @param address
     * @return
     */
    List<AddressDTO> getAddressList(AddressQuery address);

    /**
     * 插入地址
     * @param query
     * @return
     */
    List<AddressDTO> addAddress(Address query);


    List<AddressDTO> deleteAddress(AddressQuery address);

    List<AddressDTO> updateAddress(AddressQuery address);
}
