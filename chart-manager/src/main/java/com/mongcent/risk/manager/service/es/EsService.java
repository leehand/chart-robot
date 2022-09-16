package com.mongcent.risk.manager.service.es;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongcent.core.commons.exception.MongcentException;
import com.mongcent.core.commons.util.HttpUtil;
import com.mongcent.es.request.ESActuator;
import com.mongcent.es.request.ESApi;
import com.mongcent.es.request.ESRequest;
import com.mongcent.es.util.MD5Util;
import com.mongcent.risk.manager.entity.TCategory;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.mapper.TCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongcent.risk.common.RobotConstants.*;

@Service
public class EsService {

    @Resource
    private ESActuator esActuator;

    private static Logger LOGGER = LoggerFactory.getLogger(EsService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public void add(HashMap<String,Object> param) {
//        esActuator.execute("add_qa",param);
        qaPut(ESApi.getByName("add_qa"), param);
    }


    public JSONObject search(String param, PageBean pageBean ) {
//        esActuator.execute("add_qa",param);
        return qaSearch(ESApi.getByName("search_qa"), param,pageBean);
    }


    public JSONObject qaPut(ESRequest esRequest, Map<String, Object> paramMap) {
        String esUrl=esActuator.esUrl;
        String esIndex=esActuator.esIndex;
        Assert.notNull(esActuator.esUrl, "esUrl不能为空");
        ESRequest.Method method = esRequest.getMethod();
        Assert.notNull(method, "method不能为空");
        Assert.notNull(esRequest.getUri(), "uri不能为空");
        String url = esUrl + "/" + esRequest.getUri().replace("qa_index",esIndex);

        JSONObject params = esRequest.getParam();
        /**
         * {
         *       "question":"aa",
         *       "similar_names":[],
         *       "q_id":"bb"
         *     }
         */
        params.put(questionStr,paramMap.get(questionStr));
        params.put(similarNames,paramMap.get(similarNames));
        params.put(qidStr,paramMap.get(qidStr));


        url=url.replace("qa_id", (String)paramMap.get(qidStr));
        LOGGER.info("正在请求ES：" + esRequest.getUri() + "\n\tparam：" + params);

        try {

            return HttpUtil.doPost(url, params.toJSONString(), JSONObject.class);

        } catch (HttpUtil.HttpException e) {
            LOGGER.warn("请求ES失败！" + e.getMessage());
            throw new MongcentException(e);
        }
    }


    public JSONObject qaSearch(ESRequest esRequest, String keyword,PageBean pageBean) {
        String esUrl=esActuator.esUrl;
        String esIndex=esActuator.esIndex;
        Assert.notNull(esActuator.esUrl, "esUrl不能为空");
        ESRequest.Method method = esRequest.getMethod();
        Assert.notNull(method, "method不能为空");
        Assert.notNull(esRequest.getUri(), "uri不能为空");
        String url = esUrl + "/" + esRequest.getUri().replace("qa_index",esIndex);

        JSONObject params = esRequest.getParam();
        /**
         *     "query": {
         *         "multi_match": {
         *           "query": "BB",
         *           "fields": [
         *             "question",
         *             "similar_names"
         *           ]
         *         }
         *       }
         */
        params.getJSONObject("query").getJSONObject("multi_match").put("query",keyword);
        params.put("from",(pageBean.getPage()-1)*pageBean.getSize());
        params.put("size",pageBean.getSize());


        LOGGER.info("正在请求ES：" + esRequest.getUri() + "\n\tparam：" + params);

        try {

            return HttpUtil.doPost(url, params.toJSONString(), JSONObject.class);

        } catch (HttpUtil.HttpException e) {
            LOGGER.warn("请求ES失败！" + e.getMessage());
            throw new MongcentException(e);
        }
    }



}
