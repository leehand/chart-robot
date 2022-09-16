package com.mongcent.risk.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * t_category
 * @author 
 */
@Data
public class TCategory implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 类目名称
     */
    private String category;

    /**
     * 父类目id
     */
    private Integer pid;

    /**
     * 类目层级
     */
    private Integer level;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    @TableField(exist = false)
    private List<TCategory> children;

    private static final long serialVersionUID = 1L;
}