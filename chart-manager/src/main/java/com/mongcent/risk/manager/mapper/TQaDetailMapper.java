package com.mongcent.risk.manager.mapper;


import com.mongcent.risk.manager.entity.TQaDetail;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface TQaDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TQaDetail record);

    int insertSelective(TQaDetail record);

    TQaDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TQaDetail record);

    int updateByPrimaryKey(TQaDetail record);

    List<TQuestionAnswer> selectByPage(Integer answerStatus, String userName, String question, int index, int size);

    int getCount(Integer answerStatus, String userName, String question, int index, int size);
}