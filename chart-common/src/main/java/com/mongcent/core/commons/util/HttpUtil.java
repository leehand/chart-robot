/**
 *
 */
package com.mongcent.core.commons.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongcent.core.commons.exception.MongcentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Author: hezishan
 * Date: 2018/5/11.
 * Description: http工具类
 **/
@Slf4j
public class HttpUtil {


    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(2000)
            .setSocketTimeout(10000)
            .build();
    private static CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build();

    public static String doGet(String url) throws HttpException {
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            return EntityUtils.toString(httpResponse.getEntity(), "gb2312");
        } catch (Exception e) {
            log.error("访问失败：" + url);
            throw new HttpException(e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.getEntity().getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T doGet(String url, Map<String, String> formData, Header header, Class<T> clazz) throws HttpException {
        CloseableHttpResponse httpResponse = null;
        try {
            url = url + "?" + EntityUtils.toString(mapToParam(formData), "utf-8");
            HttpGet httpGet = new HttpGet(url);
            if (header != null) {
                httpGet.setHeader(header);
            }
            System.out.println(url);
            httpResponse = httpClient.execute(httpGet);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code != 200) {
                throw new HttpException(String.valueOf(code) + "  " + EntityUtils.toString(httpResponse.getEntity()));
            }
            return JSONObject.parseObject(httpResponse.getEntity().getContent(), clazz);
        } catch (Exception e) {
            log.error("访问失败：" + url);
            throw new HttpException(e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.getEntity().getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T doPostFormData(String url, Map<String, String> formData, Class<T> clazz) throws HttpException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpPost.setEntity(mapToParam(formData));
            httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return JSONObject.parseObject(result, clazz);
        } catch (Exception e) {
            log.error("访问失败：" + url);
            throw new HttpException(e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.getEntity().getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T doPost(String url, Object body, Class<T> clazz) throws HttpException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        try {
            StringEntity se = new StringEntity(JSON.toJSONString(body), "utf-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);
            httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return JSONObject.parseObject(result, clazz);
        } catch (Exception e) {
            throw new HttpException(e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.getEntity().getContent().close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static <T> T doPost(String url, String body, Class<T> clazz) throws HttpException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        try {
            if (body == null) {
                body = "{}";
            }
            StringEntity se = new StringEntity(body, "utf-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);
            httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return JSONObject.parseObject(result, clazz);
        } catch (Exception e) {
            throw new HttpException(e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.getEntity().getContent().close();
                }
            } catch (Exception e) {
            }
        }
    }



    public static <T> T doPut(String url, String body, Class<T> clazz) throws HttpException {
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse httpResponse = null;
        try {
            if (body == null) {
                body = "{}";
            }
            StringEntity se = new StringEntity(body, "utf-8");
            se.setContentType("application/json");
            httpPut.setEntity(se);
            httpResponse = httpClient.execute(httpPut);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return JSONObject.parseObject(result, clazz);
        } catch (Exception e) {
            throw new HttpException(e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.getEntity().getContent().close();
                }
            } catch (Exception e) {
            }
        }
    }

    private static StringEntity mapToParam(Map<String, String> formData) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(nameValuePairs, "utf-8");
    }

    /**
     * HTTP请求异常
     */
    public static class HttpException extends Exception {
        HttpException(String msg) {
            super(msg);
        }

        HttpException(Throwable throwable) {
            super(throwable);
        }
    }

}
