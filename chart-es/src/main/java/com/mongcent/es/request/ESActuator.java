package com.mongcent.es.request;

import com.alibaba.fastjson.JSONObject;
import com.mongcent.core.commons.exception.MongcentException;
import com.mongcent.core.commons.util.HttpUtil;
import com.mongcent.es.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @Description ES执行器。通过HTTP接口调用
 * @Author zl
 * @Date 2019/7/16
 **/
@Slf4j
@Component
public class ESActuator {

    /**
     * es的Ip和端口
     */
    @Value("${mongcent.statistics.es.esurl}")
    public  String esUrl;

    @Value("${es_index}")
    public String esIndex;

    /**
     * 执行ES请求
     *
     * @param esRequest
     * @return
     */
    public JSONObject execute(ESRequest esRequest, Map<String, String> paramMap) {
        Assert.notNull(esUrl, "esUrl不能为空");
        ESRequest.Method method = esRequest.getMethod();
        Assert.notNull(method, "method不能为空");
        Assert.notNull(esRequest.getUri(), "uri不能为空");
        String url = esUrl + "/" + esRequest.getUri().replace("qa_index",esIndex);
        String allParams;
        if (esRequest.getParam() == null) {
            allParams = "{}";
        } else {
            allParams = replaceParam(esRequest.getParam().toJSONString(), paramMap);
        }
        url=url.replace("qa_id", MD5Util.getMD5Str(allParams));
        log.info("正在请求ES：" + esRequest.getUri() + "\n\tparam：" + allParams);
        try {
            System.out.println(allParams);
            return HttpUtil.doPost(url, allParams, JSONObject.class);

        } catch (HttpUtil.HttpException e) {
            log.warn("请求ES失败！" + e.getMessage());
            throw new MongcentException(e);
        }
    }




    /**
     * 执行ES请求
     *
     * @param name
     * @param param 动态参数
     * @return
     */
    public JSONObject execute(String name, Map<String, String> param) {
        return execute(ESApi.getByName(name), param);
    }

    /**
     * 将字符串里的变量替换成变量值
     *
     * @param s
     * @param param
     * @return
     */
    private String replaceParam2(String s, Map<String, String> param) {
        String regex = "\\{\\{(.+?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        StringBuffer msg = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1).replaceAll(" ", "");
            String value = param.get(name);
            if (value == null) {
//                throw new IllegalArgumentException("找不到参数" + name);
                value = "";
            }
            matcher.appendReplacement(msg, value);
        }
        matcher.appendTail(msg);
        return msg.toString();
    }



    private String replaceParam(String s, Map<String, String> param) {
        String regex = "\\{\\{(.+?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        StringBuffer msg = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1).replaceAll(" ", "");
            String value = param.get(name);
            if (value == null) {
//                throw new IllegalArgumentException("找不到参数" + name);
                value = "";
            }
            matcher.appendReplacement(msg, value);
        }
        matcher.appendTail(msg);
        return msg.toString();
    }

}
