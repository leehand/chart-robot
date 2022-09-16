package com.mongcent.core.commons.constant;

/**
 * 返回结果常量
 * <p>
 * 常见错误标识
 * <p>
 * 尽量保持和http code含义一致
 */
public interface ApiConsts {

    /**
     * 定义API接口返回的数据健
     */
     String CODE_KEY = "code";
     String MSG_KEY = "msg";
     String DATA_KEY = "data";
     String TOKEN_KEY = "token";
     String USERNAME_KEY = "username";
     String SESSION_KEY = "spring:session:sessions:";
     String CURRENT_USER_KEY = "current_user";
     String PERMISSIONS_KEY = "permissions";

     String HEADER_TOKEN_KEY = "x-token";
    /**
     * 处理成功
     */
    int CODE_SUCCESS = 200;

    /**
     * 请求错误
     */
    int CODE_ERR = 400;

    /**
     * 请求未授权
     */
    int CODE_UNAUTHORIZED = 401;

    /**
     * 服务器错误
     */
    int CODE_SERVER_ERR = 500;

    /**
     * 需要重定向
     */
    int CODE_REDIRECT = 303;

    /**
     * 访问受限
     */
    int CODE_UNLOGIN = 403;

    /**
     * 未找到资源
     */
    int CODE_404 = 404;

    /**
     * 处理成功的默认信息
     */
    String MSG_OK = "success";

    /**
     * TOKEN已经过期，或者不存在
     */
    int CODE_NO_TOKEN = 999;

    /**
     * 登录失败
     */
    int CODE_LGON_FAILURE = 900;

}
