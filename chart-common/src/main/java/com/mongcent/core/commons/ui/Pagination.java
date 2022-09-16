package com.mongcent.core.commons.ui;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 表格分页封装
 * @Author zl
 * @Date 2018/11/28
 **/
@Data
public class Pagination {
    /**
     * 总条目数
     */
    private long total = 0;

    /**
     * 数据
     */
    private List<?> rows = new ArrayList<>();

    /**
     * 存放一些自定义的数据 。扩展用
     */
    private Object data;

    public Pagination() {
    }

    public Pagination(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
}
