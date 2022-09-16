package com.mongcent.risk.manager.mapper;


import com.mongcent.risk.manager.entity.TRobotCategory;
import org.springframework.stereotype.Component;


@Component
public interface TRobotCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRobotCategory record);

    int insertSelective(TRobotCategory record);

    TRobotCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRobotCategory record);

    int updateByPrimaryKey(TRobotCategory record);
}