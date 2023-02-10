package com.mongcent.risk.manager.service.gpt;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GPTService {

   public  static String token="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";

   static  CloseableHttpClient httpClient = HttpClients.createDefault();

    private static Logger LOGGER = LoggerFactory.getLogger(GPTService.class);
    private static Gson gson = new GsonBuilder()
            .create();



    public String getgpt(String token,String keyword){
        LOGGER.info("token:"+token);

        OpenAiService service = new OpenAiService(token,60*3);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(keyword)
                .model("text-davinci-003")
                .temperature(0.9)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .maxTokens(2000)
                .build();
        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        String result="";

        for (CompletionChoice aa:choices
        ) {
            result = aa.getText();


        }
        LOGGER.info("token:"+token);
        LOGGER.info(result);

        result=result.substring(result.indexOf("答：")+2);


        return result;
    }


    public String getgptByPhp(String keyword){
        String url = "http://api.crm.test.lthink.net/api/CrmOpenApi/common/GptOpenApi";

        //请求ti
        JSONObject parammap = new JSONObject();
        parammap.put("is_get",1);
        parammap.put("content", keyword);


        String result = doPost(url, parammap.toString());
        LOGGER.info("content: "+keyword);
        LOGGER.info(result);




        return result;
    }


    public String doPost(String url ,String json) {
        //创建httpclient对象
        if(httpClient==null){
            CloseableHttpClient httpClient = HttpClients.createDefault();
        }

        CloseableHttpResponse response = null;
        String res = "";

        try {
            // 创建 httppost请求
            HttpPost post = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            // 执行http请求

            response = httpClient.execute(post);



            res = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject parse = (JSONObject) JSON.parse(res);
            res = parse.getString("data");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }





}
