package com.mongcent.core.commons.util;

import com.mongcent.core.commons.constant.Constants;

/**
 * @Description
 * @Author zl
 * @Date 2018/11/28
 **/
public class AdminUtil {
    /**
     * 根据ID判断用户是否为超级管理员
     *
     * @param userId
     * @return
     */
    public static boolean isAdmin(long userId) {
        return userId == Constants.ADMIN_ID;
    }
}
