package com.mongcent.es;

import com.alibaba.fastjson.JSONObject;
import com.mongcent.es.parse.ESParser;
import com.mongcent.es.request.ESApi;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @Description
 * @Author zl
 * @Date 2019/7/16
 **/
@Component
public class ESLoader {

    private ESParser esParser = new ESParser();

    @PostConstruct
    public void load() throws IOException {
        JSONObject jsonObject = esParser.parseFromFile();
        for (String key : jsonObject.keySet()) {
            ESApi.add(key, esParser.jsonToRequest(jsonObject.getJSONObject(key)));
        }
    }
}
