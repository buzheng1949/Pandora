package com.gdut.pandora.utils;

import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.AddressDTO;
import com.gdut.pandora.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by buzheng on 18/3/31.
 */
@Slf4j
@Component
public class UserUtils {

    @Autowired
    private AddressService addressService;

    /**
     * 注册的用户是否是有效用户
     *
     * @param userQuery
     * @return
     */
    public static boolean isValidUser(UserQuery userQuery) {
        boolean result = Boolean.FALSE;
        if (userQuery == null) {
            return result;
        }
        if (StringUtils.isEmpty(userQuery.getPassword()) || StringUtils.isEmpty(userQuery.getPhone())) {
            log.error("the user is not valid,the needed message is empty");
            return result;
        }
        result = true;
        return result;

    }

    /**
     * @param user 补全用户的默认地址信息
     */
    public void addAddressMessageToUser(User user) {
        if (user.getId() != null) {
            AddressQuery addressQuery = new AddressQuery();
            addressQuery.setDefaultAddress((byte) 1);
            if (user.getId() == null) {
                return;
            }
            addressQuery.setUid(user.getId().longValue());
            List<AddressDTO> addressDTOs = addressService.getAddressList(addressQuery);
            if (!CollectionUtils.isEmpty(addressDTOs)) {
                AddressDTO addressDTO = addressDTOs.get(0);
                user.setAddress(addressDTO);
            }
        }

    }
}
