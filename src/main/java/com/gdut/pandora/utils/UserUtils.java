package com.gdut.pandora.utils;

import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by buzheng on 18/3/31.
 */
@Slf4j
public class UserUtils {

    /**
     * 注册的用户是否是有效用户
     * @param userQuery
     * @return
     */
    public static boolean isValidUser(UserQuery userQuery) {
        boolean result = Boolean.FALSE;
        if (userQuery == null) {
            return result;
        }
        if (StringUtils.isEmpty(userQuery.getPassword()) || StringUtils.isEmpty(userQuery.getPhone()) || StringUtils.isEmpty(userQuery.getUserName())) {
            log.error("the user is not valid,the needed message is empty");
            return result;
        }
        result = true;
        return result;

    }
}
