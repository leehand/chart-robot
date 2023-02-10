package openai;

import cn.hutool.http.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ChatGPTTest1 {

    String token="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";



    @Test
    public  void  test1(){
        /**
         *       json.put("temperature",0.9);
         *             json.put("max_tokens",2048);
         *             json.put("top_p",1);
         *             json.put("frequency_penalty",0.0);
         *             json.put("presence_penalty",0.6);
         */
//
//        OpenAiService service = new OpenAiService(token,60*3);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("如何提高伊肤泉在医美机构的销售额")
//                .model("text-davinci-003")
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.6)
//                .maxTokens(2000)
//                .build();
//        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
//        for (CompletionChoice aa:choices
//             ) {
//            System.out.println(aa.toString());
//
//        }

//        String result="的看法\\n\\n答：我认为设置失业税是有必要的，因为它可以帮助政府筹集资金，来支持没有工作的人。同时，这种税收可以鼓励这些人尽快找到工作，帮助他们走出失业的困境。失业税也有助于维护社会公平，促使有收入的人缴纳税款，从而用于支持其他劳动力收入不足的人。";
//
//
//        result=result.substring(result.indexOf("答:")+2);
//        System.out.println(result);

        sendPost("http://api.crm.test.lthink.net/api/CrmOpenApi/common/GptOpenApi","伊肤泉补水文案");
    }


    public  String sendPost(String url,String content){
        //定义接收数据
        String result ="";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        JSONObject json=new JSONObject();
        json.put("content",content);
        json.put("is_get",1);
        StringEntity entity = new StringEntity(json.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("xx", "xxx");
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity1 = response.getEntity();
                result = EntityUtils.toString(
                        response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
//            result.put("error", "连接错误！");
        }
        return result;
    }




    /**
     * 模拟Post请求，进行url测试
     */
    @Test
    public void testPostRequest() {
        String url = "http://api.crm.test.lthink.net/api/CrmOpenApi/common/GptOpenApi";

        //请求ti
        JSONObject parammap = new JSONObject();
        parammap.put("is_get",1);
        parammap.put("content", "伊肤泉补水文案");


        String str = doPost(url, parammap.toString());
        // 输出响应内容
        System.out.println(str);

    }

    public String doPost(String url ,String json) {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
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
