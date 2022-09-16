package com.mongcent.core.commons.ui;

import lombok.Data;

import java.util.List;

/**
 * @Description 封装树形控件，供前端使用
 * @Author zl
 * @Date 2018/11/27
 **/
@Data
public class Tree {
    /**
     * 树的ID(唯一)
     * 用数据库的ID，如果有ID是重复的，那么修改次要数据的ID
     */
    private String id;

    /**
     * 树的父ID
     */
    private String pid;

    /**
     * 显示的名称
     */
    private String label;

    /**
     * 图标
     */
    private String icon;

    /**
     * 真正的数据
     */
    private Object data;
    /**
     * 下一级
     */
    private List<Tree> children;

    public Tree(String id, String pid, String label, Object data) {
        this.id = id;
        this.pid = pid;
        this.label = label;
        this.data = data;
    }
}
