package com;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkim_1_0.models.PrivateDataValue;
import com.aliyun.dingtalkim_1_0.models.SendInteractiveCardRequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaPair;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;


import com.aliyun.tea.*;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;
import com.aliyun.dingtalkim_1_0.*;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiImChatScencegroupInteractivecardCallbackRegisterRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiImChatScencegroupInteractivecardCallbackRegisterResponse;
import org.junit.Test;

public class DingCardTest1 {

    public static final String 		 AgentId= "14388001";
    public static final String 		 AppKey= "dinglfrbyyxwkl0qevyf";
    public static final String 		 AppSecret= "1MhJBFxGZgEW17_x3JgKft0oxSYpQ7-WE2DMeqv9ywtekWDB-JF-Gltu9VXEyaGM";
    //	public static final String 		 CardTemplateId= "536b409e-dfd2-44a0-a023-88c961035a65";
//	public static final String 		 CardTemplateId= "d490cb55-663e-4650-b005-b3a217dbda3b";
    public static final String 		 CardTemplateId= "e50ec093-1170-4dab-9b57-4fa98f20c9f6";
    public static final Integer 		 ConversationType= 0;
    public static final Integer 		 maxPage= 5;
    public static final Integer 		 size= 2;

    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkim_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkim_1_0.Client(config);
    }





    public  String getAccessToken() throws Exception {
        /**
         * AgentId
         * 12539002
         AppKey
         dinglfrbyyxwkl0qevyf
         AppSecret
         1MhJBFxGZgEW17_x3JgKft0oxSYpQ7-WE2DMeqv9ywtekWDB-JF-Gltu9VXEyaGM
         */
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(AppKey);
        request.setAppsecret(AppSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    @Test
    public void test1() throws Exception{
        sendMessage1("09603214021209159","dinglfrbyyxwkl0qevyf");
    }

    @Test
    public void test2() throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/im/chat/scencegroup/interactivecard/callback/register");
        OapiImChatScencegroupInteractivecardCallbackRegisterRequest req = new OapiImChatScencegroupInteractivecardCallbackRegisterRequest();
        req.setCallbackUrl("http://120.79.23.46:12580/v1/dingding/cardUpdate");
//        req.setApiSecret("bgRtxxxx");
//        req.setForceUpdate(true);
        req.setCallbackRouteKey("bbb");
        OapiImChatScencegroupInteractivecardCallbackRegisterResponse rsp = client.execute(req, getAccessToken());
        System.out.println(rsp.getBody());
    }





    public void sendMessage1(String userId,String robotCode) throws Exception {

        com.aliyun.dingtalkim_1_0.Client client = createClient();
        SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
        sendInteractiveCardHeaders.xAcsDingtalkAccessToken = getAccessToken();
        SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions cardOptions = new SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions()
                .setSupportForward(true);
        java.util.Map<String, String> atOpenIds = TeaConverter.buildMap(
                new TeaPair("key", "{123456:\"钉三多\"}")
        );
        java.util.Map<String, String> privateDataValueKeyCardMediaIdParamMap = TeaConverter.buildMap(
                new TeaPair("key", "test")
        );
        java.util.Map<String, String> privateDataValueKeyCardParamMap = TeaConverter.buildMap(
                new TeaPair("key", "test")
        );
        PrivateDataValue privateDataValueKey = new PrivateDataValue()
                .setCardParamMap(privateDataValueKeyCardParamMap)
                .setCardMediaIdParamMap(privateDataValueKeyCardMediaIdParamMap);
        java.util.Map<String, PrivateDataValue> privateData = TeaConverter.buildMap(
                new TeaPair("privateDataValueKey", privateDataValueKey)
        );



        java.util.Map<String, String> cardDataCardParamMap = TeaConverter.buildMap(
                new TeaPair("content1", "1111"),
                new TeaPair("content2", "2222"),
                new TeaPair("content3", "3333")
        );
        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                .setCardParamMap(cardDataCardParamMap);

        SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                .setCardTemplateId(CardTemplateId)

                .setReceiverUserIdList(java.util.Arrays.asList(
                        userId
                ))
                .setOutTrackId("trackId")
                .setRobotCode(robotCode)
                .setConversationType(ConversationType)
                .setCardData(cardData)
                .setPrivateData(privateData)
                .setChatBotId("123")
                .setUserIdType(1)
                .setAtOpenIds(atOpenIds)
                .setCardOptions(cardOptions);

        try {
            SendInteractiveCardResponse sendInteractiveCardResponse = client.sendInteractiveCardWithOptions(sendInteractiveCardRequest, sendInteractiveCardHeaders, new RuntimeOptions());
            System.out.println();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
            }

        }
    }





}
