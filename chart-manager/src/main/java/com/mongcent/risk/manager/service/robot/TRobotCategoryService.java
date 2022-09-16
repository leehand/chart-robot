package com.mongcent.risk.manager.service.robot;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongcent.risk.manager.entity.TRobotCategory;
import com.mongcent.risk.manager.mapper.TRobotCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("robot")
public class TRobotCategoryService {

    @Autowired
    TRobotCategoryMapper tRobotCategoryMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(TRobotCategoryService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public Integer add(TRobotCategory tRobotCategory) {

        int count = tRobotCategoryMapper.insert(tRobotCategory);
        return count;

    }


    public Integer update(TRobotCategory tRobotCategory) {

        int count = tRobotCategoryMapper.updateByPrimaryKeySelective(tRobotCategory);
        return count;


    }


    public Integer delete(Integer id) {

        int count = tRobotCategoryMapper.deleteByPrimaryKey(id);
        return count;


    }

}
