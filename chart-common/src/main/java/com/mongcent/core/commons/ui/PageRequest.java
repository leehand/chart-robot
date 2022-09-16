package com.mongcent.core.commons.ui;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 分页请求参数
 * @Author zl
 * @Date 2018/11/28
 **/
@Data
public class PageRequest {

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 每页个数
     */
    private int pageSize = 10;

    /**
     * 从第几条开始查询
     */
    private long skip;

    private Object entity;

    private Map<String, Object> condition = new HashMap<>();

    public PageRequest() {
    }

    public PageRequest(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PageRequest(Map<String, String> map) {
        //解析当前页
        String currentString = map.get("currentPage");
        if (StringUtils.isNoneBlank(currentString)) {
            this.currentPage = Integer.parseInt(currentString);
            map.remove("currentPage");
        }
        //解析每页个数
        String pageSizeString = map.get("pageSize");
        if (StringUtils.isNoneBlank(pageSizeString)) {
            this.pageSize = Integer.parseInt(pageSizeString);
            map.remove("pageSize");
        }
        condition.putAll(map);
    }

    public long getSkip() {
        return (currentPage - 1) * pageSize;
    }
}
