package com.mongcent.es.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongcent.es.request.ESRequest;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @Description ES解析器，将json解析成java
 * @Author zl
 * @Date 2019/7/16
 **/
public class ESParser {

    private final String filename = "es.json";

    public JSONObject parseFromFile() throws IOException {
        return JSON.parseObject(this.getClass().getClassLoader().getResource(filename).openStream(), JSONObject.class);
    }

    public ESRequest jsonToRequest(JSONObject jsonObject) {
        String uri = jsonObject.getString("uri");
        Assert.notNull(uri, "uri不能为空，请检查es.json");
        JSONObject paramJson = jsonObject.getJSONObject("param");
        String[] bases = uri.trim().split(" +", 2);
        Assert.state(bases.length == 2, "uri解析错误：" + uri);
        ESRequest esRequest = new ESRequest();
        ESRequest.Method method = ESRequest.Method.valueOf(bases[0].toUpperCase());
//        if (method != ESRequest.Method.POST) {
//            throw new IllegalArgumentException("目前只支持POST方法：" + uri);
//        }
        esRequest.setMethod(method);
        esRequest.setUri(bases[1]);
        esRequest.setParam(paramJson);
        return esRequest;
    }
}
