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
        if (query == null) {
            throw new RuntimeException("请登陆进行查询或者传入当前登陆用户手机号");
        }
        if (query.getUserId() == null) {
            UserDTO userDTO = getLoginUserMessage(String.valueOf(query.getPhone()));
            if (userDTO != null) {
                query.setUserId(Long.valueOf(userDTO.getId()));
            } else {
                throw new RuntimeException("请用户进行登陆后查询");
            }
        }
        AddressQuery realQuery = new AddressQuery();
        realQuery.setUserId(query.getUserId());
        List<Address> addressesResult = addressMapper.list(realQuery);
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
        if (query.getUserId() == null) {
            UserDTO userDTO = getLoginUserMessage(String.valueOf(query.getPhone()));
            if (userDTO != null) {
                query.setUserId(Long.valueOf(userDTO.getId()));
            }
        }
        AddressQuery selectQuery = new AddressQuery();
        selectQuery.setUserId(query.getUserId());
        List<Address> addressDTOs = addressMapper.list(selectQuery);
        //等于空列表的情况下 把插入的设置为默认地址
        if (CollectionUtils.isEmpty(addressDTOs)) {
            query.setDefaultAddress((byte) 1);

        } else {
            query.setDefaultAddress((byte) (0));
        }
        query.setCreateTime(TimeUtils.getCurrentTime());
        query.setUpdateTime(TimeUtils.getCurrentTime());
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
    public List<AddressDTO> updateAddress(AddressQuery query) {
        if (query.getDefaultAddress().byteValue() == 1) {
            AddressQuery addressQuery = new AddressQuery();
            addressQuery.setUserId(query.getUserId());
            addressQuery.setDefaultAddress((byte) 1);
            List<Address> addresses = addressMapper.list(addressQuery);
            if (!CollectionUtils.isEmpty(addresses)) {
                for (Address address : addresses) {
                    AddressQuery updateQuery = new AddressQuery();
                    updateQuery.setId(address.getId());
                    updateQuery.setDefaultAddress((byte) 0);
                    addressMapper.update(updateQuery);
                }
            }
        }

        int res = addressMapper.update(query);
        List<AddressDTO> addressList = getAddressList(query);
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
