package com.mongcent.risk.manager.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_robot
 * @author 
 */
@Data
public class TRobot implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 机器人名称
     */
    private String name;

    /**
     * 机器人url
     */
    private String url;

    /**
     * 机器人作用
     */
    private String effect;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}