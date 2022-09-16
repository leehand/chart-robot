package com.mongcent.risk.manager.service.robot;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mongcent.risk.manager.entity.TCategory;
import com.mongcent.risk.manager.mapper.TCategoryMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service
@DS("robot")
public class TCategoryService {

    @Autowired
    TCategoryMapper tCategoryMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(TCategoryService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public Integer add(TCategory tCategory) {

        int count = tCategoryMapper.insert(tCategory);
        return count;

    }


    public Integer update(TCategory tCategory) {

        int count = tCategoryMapper.updateByPrimaryKeySelective(tCategory);
        return count;


    }


    public Integer delete(Integer id) {

        int count = tCategoryMapper.deleteByPrimaryKey(id);
        return count;


    }

    public List<TCategory>  getList(){
        List<TCategory> tCategories = tCategoryMapper.selectAll();
        return tCategories;
    }



    public List<TCategory>  getTree(){
        List<TCategory> tCategories = tCategoryMapper.selectAllTree();
        return tCategories;
    }



}
