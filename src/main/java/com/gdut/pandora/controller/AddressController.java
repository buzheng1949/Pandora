package com.gdut.pandora.controller;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Address;
import com.gdut.pandora.domain.Banner;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.query.BannerQuery;
import com.gdut.pandora.domain.result.AddressDTO;
import com.gdut.pandora.service.AddressService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzheng on 18/4/13.
 */
@RestController
@ResponseBody
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping("/query")
    @NeedLogin
    public ServerResponse<List<AddressDTO>> query(AddressQuery address) {
        List<AddressDTO> addressDTOs = new ArrayList<>();
        try {
            addressDTOs = addressService.getAddressList(address);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess("查询成功", addressDTOs);
    }

    @RequestMapping("/add")
    public ServerResponse<List<AddressDTO>> add(Address address) {
        List<AddressDTO> addressDTOs = new ArrayList<>();
        if(address.getUserId() == null){
            return ServerResponse.createByErrorMessage("请传入用户ID");
        }
        try {
            addressDTOs = addressService.addAddress(address);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("插入失败，请重试");
        }
        return ServerResponse.createBySuccess("新增用户地址成功", addressDTOs);
    }

    @RequestMapping("/update")
    public ServerResponse<List<AddressDTO>> update(AddressQuery address) {
        List<AddressDTO> addressDTOs = new ArrayList<>();
        try {
            if (address.getId() == null || address.getUserId() == null) {
                return ServerResponse.createByErrorMessage("必须传入地址ID以及用户ID");
            }
            addressDTOs = addressService.updateAddress(address);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess("更新成功", addressDTOs);
    }

    @RequestMapping("/delete")
    public ServerResponse<List<AddressDTO>> delete(AddressQuery address) {
        List<AddressDTO> addressDTOs = new ArrayList<>();
        try {
            addressDTOs = addressService.deleteAddress(address);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("更新用户信息失败，请排查");
        }
        return ServerResponse.createBySuccess("删除成功", addressDTOs);
    }
}
