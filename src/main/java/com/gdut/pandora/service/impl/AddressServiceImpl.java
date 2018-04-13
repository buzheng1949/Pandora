package com.gdut.pandora.service.impl;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.domain.Address;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.AddressDTO;
import com.gdut.pandora.domain.result.UserDTO;
import com.gdut.pandora.mapper.AddressMapper;
import com.gdut.pandora.service.AddressService;
import com.gdut.pandora.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by buzheng on 18/4/13.
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    @NeedLogin
    public List<AddressDTO> getAddressList(AddressQuery query) {
        List<AddressDTO> res = new ArrayList<>();
        if (query == null || (query.getUserId() == null && query.getPhone() == null)) {
            throw new RuntimeException("请传入查询用户ID");
        }
        if(query.getUserId() == null){
            UserDTO userDTO = getLoginUserMessage(String.valueOf(query.getPhone()));
            if(userDTO != null){
                query.setUserId(Long.valueOf(userDTO.getId()));
            }
        }
        List<Address> addressesResult = addressMapper.list(query);
        if (CollectionUtils.isEmpty(addressesResult)) {
            return res;
        }
        for (Address address : addressesResult) {
            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(address, addressDTO);
            res.add(addressDTO);
        }
        return res;
    }

    public List<AddressDTO> addAddress(Address query) {
        if (query == null || query.getPhone() == null) {
            throw new RuntimeException("不能传入无信息的地址");
        }
        if (StringUtils.isEmpty(query.getAddress())) {
            throw new RuntimeException("用户的地址不能为空");
        }
        if(query.getUserId() == null){
            UserDTO userDTO = getLoginUserMessage(String.valueOf(query.getPhone()));
            if(userDTO != null){
                query.setUserId(Long.valueOf(userDTO.getId()));
            }
        }
        query.setCreateTime(TimeUtils.getCurrentTime());
        query.setUpdateTime(TimeUtils.getCurrentTime());
        if (query.getDefaultAddress() == null) {
            query.setDefaultAddress((byte) 0);
        }
        int res = addressMapper.insert(query);
        if (res <= 0) {
            throw new RuntimeException("插入用户地址失败，请重试");
        }
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.setPhone(query.getPhone());
        addressQuery.setUserId(query.getUserId());
        addressQuery.setId(query.getId());
        List<AddressDTO> addressList = getAddressList(addressQuery);
        return addressList;
    }

    @Override
    public List<AddressDTO> deleteAddress(AddressQuery address) {
        if (address == null || address.getId() == null) {
            throw new RuntimeException("请传入地址列表ID");
        }
        int res = addressMapper.delete(address.getId());
        if (res <= 0) {
            throw new RuntimeException("地址删除失败");
        }
        List<AddressDTO> addressList = getAddressList(address);
        return addressList;
    }

    @Override
    public List<AddressDTO> updateAddress(AddressQuery address) {
        int res = addressMapper.update(address);
        List<AddressDTO> addressList = getAddressList(address);
        return addressList;
    }

    /**
     * 用户是否登陆成功
     *
     * @return
     */
    private UserDTO getLoginUserMessage(String phone) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(phone);
        return user;
    }
}
