package com.mongcent.risk.manager.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_qa_detail
 * @author 
 */
@Data
public class TQaDetail implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 机器人id
     */
    private Integer robotId;

    /**
     * 提问人id
     */
    private String userId;

    /**
     * 提问人名称
     */
    private String userName;

    /**
     * 问题
     */
    private String question;

    /**
     * 状态 -1:无答案 ; 1:推荐;   2:知识点
     */
    private Integer answerStatus;

    /**
     * 命中的问题列表
     */
    private Object questions;

    /**
     * 答案
     */
    private String answer;

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