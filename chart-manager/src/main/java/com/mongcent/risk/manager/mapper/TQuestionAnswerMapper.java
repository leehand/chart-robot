package com.mongcent.risk.manager.mapper;


import com.mongcent.risk.manager.entity.TQuestionAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface TQuestionAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TQuestionAnswer record);

    void insertAll(@Param("list")List<TQuestionAnswer> list);

    TQuestionAnswer selectByPrimaryKey(Integer id);


    List<TQuestionAnswer> selectByIdList(@Param("list")List<String> list);


    List<TQuestionAnswer> selectByPage(@Param("categoryId")Integer categoryId,
                                       @Param("keyword")String keyword,
                                       @Param("status")Integer status,
                                       @Param("startRow")Integer startRow, @Param("size")Integer size);

    int updateByPrimaryKeySelective(TQuestionAnswer record);

    int updateByPrimaryKey(TQuestionAnswer record);

    int getCount(@Param("categoryId")Integer categoryId,
                 @Param("keyword")String keyword,
                 @Param("status")Integer status,
                 @Param("startRow")Integer startRow, @Param("size")Integer size);
}