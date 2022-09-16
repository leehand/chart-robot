package com.mongcent.risk.manager.mapper;


import com.mongcent.risk.manager.entity.TCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCategory record);

    int insertSelective(TCategory record);

    TCategory selectByPrimaryKey(Integer id);

    List<TCategory> selectAll();

    List<TCategory> selectAllTree();

    List<TCategory> findTreeByParentId();

    int updateByPrimaryKeySelective(TCategory record);

    int updateByPrimaryKey(TCategory record);
}