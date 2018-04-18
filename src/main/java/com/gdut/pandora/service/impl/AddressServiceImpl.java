package com.gdut.pandora.service.impl;

import com.gdut.pandora.anno.NeedLogin;
import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.ReturnTypeEnum;
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
    @ReturnType(type = ReturnTypeEnum.DEFAULT)
    public List<AddressDTO> getAddressList(AddressQuery query) {
        List<AddressDTO> res = new ArrayList<>();
        if (query == null || query.getUid() == null) {
            throw new RuntimeException("请传入用户的uid进行查询");
        }
        AddressQuery realQuery = new AddressQuery();
        realQuery.setUid(query.getUid());
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
            throw new RuntimeException("传入的电话为空");
        }
        if (query.getUname() == null) {
            throw new RuntimeException("收件人姓名不能为空");
        }
        if (StringUtils.isEmpty(query.getAddress())) {
            throw new RuntimeException("用户的地址不能为空");
        }
        if (StringUtils.isEmpty(query.getDetailAddress())) {
            throw new RuntimeException("用户的地址详情不能为空");
        }
        query.setCreateTime(TimeUtils.getCurrentTime());
        query.setUpdateTime(TimeUtils.getCurrentTime());
        if (query.getHasCreated() != null && query.getHasCreated() == 1) {
            query.setDefaultAddress((byte) (0));
        } else {
            query.setDefaultAddress((byte) (1));
        }
        if (query.getIsDefault() != null && query.getIsDefault() == 1) {

            AddressQuery addressQuery = new AddressQuery();
            addressQuery.setUid(query.getUid());
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

            query.setDefaultAddress((byte) 1);
        }
        int res = addressMapper.insert(query);
        if (res <= 0) {
            throw new RuntimeException("新增地址失败，请稍后再试");
        }
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.setUid(query.getUid());
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
        if (query.getDefaultAddress() != null && query.getDefaultAddress().byteValue() == 1) {
            AddressQuery addressQuery = new AddressQuery();
            addressQuery.setUid(query.getUid());
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
