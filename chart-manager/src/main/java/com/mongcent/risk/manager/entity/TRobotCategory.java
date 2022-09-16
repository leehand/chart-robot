package com.mongcent.risk.manager.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_robot_category
 * @author 
 */
@Data
public class TRobotCategory implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 机器人id
     */
    private Integer robotId;

    /**
     * 类目id
     */
    private Integer categoryId;

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