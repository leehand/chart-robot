package com.mongcent.risk.manager.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_question_answer
 * @author 
 */
@Data
public class TQuestionAnswer implements Serializable {

    //uuid
    private String id;

    /**
     * 问题
     */
    private String questionName;

    /**
     * 相似问法
     */
    private String similarNames;

    /**
     * 答案
     */
    private String answer;

    /**
     * 关联类目id
     */
    private Integer categoryId;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 关联问题id列表
     */
    private String relationIds;

    /**
     * 相关度分值
     */
    private Double score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;


    public TQuestionAnswer() {

    }


    public TQuestionAnswer(String id,String questionName, String similarNames, String answer, Integer categoryId,Date createTime) {
        this.id = id;
        this.questionName = questionName;
        this.similarNames = similarNames;
        this.answer = answer;
        this.categoryId = categoryId;
        this.createTime = createTime;
    }


    public TQuestionAnswer(String id, String questionName, String similarNames, Double score) {
        this.id = id;
        this.questionName = questionName;
        this.similarNames = similarNames;
        this.score = score;
    }
}