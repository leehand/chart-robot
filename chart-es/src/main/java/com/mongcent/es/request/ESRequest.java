package com.mongcent.es.request;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Description 构造ES的请求
 * @Author zl
 * @Date 2019/7/16
 **/
@Data
public class ESRequest {
    /**
     * HTTP请求方式
     */
    public enum Method {
        GET, POST, PUT, DELETE,
    }

    /**
     * 请求方式
     * not null
     */
    private Method method;
    /**
     * 请求路径
     * not null
     */
    private String uri;

    /**
     * 请求的参数。以json的格式
     * enable null
     */
    private JSONObject param;
}
