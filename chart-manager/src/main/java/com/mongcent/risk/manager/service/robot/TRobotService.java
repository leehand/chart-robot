package com.mongcent.risk.manager.service.robot;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongcent.risk.manager.entity.TRobot;
import com.mongcent.risk.manager.mapper.TRobotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("robot")
public class TRobotService {

    @Autowired
    TRobotMapper tRobotMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(TRobotService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public Integer add(TRobot tRobot) {

        int count = tRobotMapper.insert(tRobot);
        return count;

    }


    public Integer update(TRobot tRobot) {

        int count = tRobotMapper.updateByPrimaryKeySelective(tRobot);
        return count;


    }


    public Integer delete(Integer id) {

        int count = tRobotMapper.deleteByPrimaryKey(id);
        return count;


    }

    public List<TRobot>  getList(){
        List<TRobot> tCategories = tRobotMapper.selectAll();
        return tCategories;
    }


}
