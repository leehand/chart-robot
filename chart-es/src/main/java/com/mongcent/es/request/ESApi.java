package com.mongcent.es.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zl
 * @Date 2019/7/16
 **/
public class ESApi {

    private static Map<String, ESRequest> esRequestMap = new HashMap<>();

    public static void add(String name, ESRequest esRequest) {
        ESApi.esRequestMap.put(name, esRequest);
    }

    public static ESRequest getByName(String name) {
        return esRequestMap.get(name);
    }

}
