package com.mongcent.risk.manager.mapper;


import com.mongcent.risk.manager.entity.TRobot;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface TRobotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRobot record);

    int insertSelective(TRobot record);

    TRobot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRobot record);

    int updateByPrimaryKey(TRobot record);

    List<TRobot> selectAll();
}