package com.mongcent.risk.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaPair;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.service.gpt.GPTService;
import com.mongcent.risk.manager.service.robot.TQuestionAnswerService;
import  java.net.URLEncoder;

import com.mongcent.risk.manager.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "v1/")
/**
 *
 * @author zhongli
 * dingding机器人api
 */
public class DingdingController2 {

	@Autowired
	TQuestionAnswerService tQuestionAnswerService;


    @Autowired
    private GPTService gptService;

    @Value("${dingding.AgentId}")
    public  String 	AgentId;
    @Value("${dingding.AppKey}")
    public  String 	AppKey;
    @Value("${dingding.AppSecret}")
    public  String 	AppSecret;




    //远信测试
//	public static final String 		 AgentId= "12539002";
//	public static final String 		 AppKey= "dingpe3gzuwesjtdhx7i";
//	public static final String 		 AppSecret= "tnUw33j5KwpAXmew5Q-IsFTmnIDhqUsdCMwFFHSFirsQoYN_PkgHOv9SqMlPLJlz";
//

	//leehand测试
//	public static final String 		 AgentId= "14388001";
//	public static final String 		 AppKey= "dinglfrbyyxwkl0qevyf";
//	public static final String 		 AppSecret= "1MhJBFxGZgEW17_x3JgKft0oxSYpQ7-WE2DMeqv9ywtekWDB-JF-Gltu9VXEyaGM";


//	public static final String 		 CardTemplateId= "536b409e-dfd2-44a0-a023-88c961035a65";
//	public static final String 		 CardTemplateId= "d490cb55-663e-4650-b005-b3a217dbda3b";
	public static final String 		 CardTemplateId= "e50ec093-1170-4dab-9b57-4fa98f20c9f6";
	public static final Integer 		 ConversationType= 0;
	public static final Integer 		 maxPage= 5;
	public static final Integer 		 size= 2;


	private  static String noQuestionStr="亲，您问的问题可以换个方式问哦";


	private static Logger LOGGER = LoggerFactory.getLogger(DingdingController2.class);


    public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkrobot_1_0.Client(config);
    }
    public static com.aliyun.dingtalkim_1_0.Client createClient2() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkim_1_0.Client(config);
    }



	@RequestMapping(value = "/dingding/robots", method = RequestMethod.POST)
	public String helloRobots(@RequestBody(required = false) JSONObject json) throws Exception {
//		System.out.println(JSON.toJSONString(json));
        LOGGER.info(JSON.toJSONString(json));
		JSONObject text= json.getJSONObject("text");
		String content = text.getString("content");
		Integer page = text.getInteger("page");
        String cardUuid = text.getString("cardUuid");
		String userId = json.get("senderStaffId").toString();
		String robotCode = json.get("robotCode").toString();
		//{"text":{"content"："发票"}，"senderStaffId":"2132"}
		System.out.println(content);

        if(page!=null && page>=1){
            page=page+1;
        }else{
            page=1;
        }

        PageBean pageBean=new PageBean();
        pageBean.setPage(page);
        pageBean.setSize(size);

        String result ="";


        try {
            result = gptService.getgptByPhp(content);
        }catch (Exception e2){
            LOGGER.error("PHP请求报错：",e2);
            try {
                result = gptService.getgpt(gptService.token, content);
            }catch (Exception e){
                LOGGER.error("直接请求报错：",e);
            }
        }



        sendMessageGPT(userId,robotCode,result);

	
		return null;
	}



    @RequestMapping(value = "/dingding/robots2", method = RequestMethod.POST)
    public String helloRobots2(@RequestBody(required = false) JSONObject json) throws Exception {
        System.out.println(JSON.toJSONString(json));

        JSONObject text= json.getJSONObject("text");
        String content = text.getString("content");
        Integer page = text.getInteger("page");
        String cardUuid = text.getString("cardUuid");
        String userId = json.get("senderStaffId").toString();
        String robotCode = json.get("robotCode").toString();
        //{"text":{"content"："发票"}，"senderStaffId":"2132"}
        System.out.println(content);

        if(page!=null && page>=1){
            page=page+1;
        }else{
            page=1;
        }

        PageBean pageBean=new PageBean();
        pageBean.setPage(page);
        pageBean.setSize(size);



        List<TQuestionAnswer> result=	tQuestionAnswerService.searchquestionanswers(content,pageBean);
        if(result.size()<=0 && page==1){
            sendMessageNoQuestion(userId,robotCode);
        }else if (result.size()==1 && page==1){
            sendMessageUnique(userId,robotCode,result);
        }else{
//			sendMessageList(userId,robotCode,result,content);

            sendMessage1(userId, robotCode, result, content,cardUuid, pageBean);

        }

        return null;
    }



    @RequestMapping(value = "/dingding/cardUpdate", method = RequestMethod.POST)
    public String cardUpdate(@RequestBody(required = false) JSONObject json) throws Exception {
        System.out.println(JSON.toJSONString(json));

        JSONObject text= json.getJSONObject("text");
        String content = text.getString("content");
        Integer page = text.getInteger("page");
        String cardUuid = text.getString("cardUuid");
        String userId = json.get("senderStaffId").toString();
        String robotCode = json.get("robotCode").toString();
        //{"text":{"content"："发票"}，"senderStaffId":"2132"}
        System.out.println(content);

        if(page!=null && page>=1){
            page=page+1;
        }else{
            page=1;
        }


        PageBean pageBean=new PageBean();
        pageBean.setPage(page);
        pageBean.setSize(size);



        List<TQuestionAnswer> result=	tQuestionAnswerService.searchquestionanswers(content,pageBean);

        if( page>1) {
            updateCard(userId, robotCode, result, content, cardUuid, pageBean);
        }


        return null;
    }




    public void sendMessageList(String userId,String robotCode,   List<TQuestionAnswer> list,String content,Integer page) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
//        String urlString = URLEncoder.encode("请问您是要咨询发票的什么内容呢","utf-8");
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		Map<String,String> msgParams=new HashMap<>();

        msgParams.put("title",content);
        msgParams.put("text","请问您是要咨询的什么内容呢");
        for(int i=0;i<list.size();i++){
            TQuestionAnswer item = list.get(i);
            msgParams.put("actionTitle"+(i+1),item.getQuestionName());
            msgParams.put("actionURL"+(i+1),"dtmd://dingtalkclient/sendMessage?content="+URLEncoder.encode(item.getQuestionName(),"utf-8"));

        }
        msgParams.put("updateURL","dtmd://dingtalkclient/sendMessage?page="+page+"&content="+URLEncoder.encode(content,"utf-8"));

		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(userId))
				.setMsgKey("sampleActionCard3")
				.setMsgParam(JSON.toJSONString(msgParams));
		try {
			BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
			System.out.println(JSON.toJSONString(batchSendOTOResponse.getBody()));
		} catch (TeaException err) {
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		} catch (Exception _err) {
			TeaException err = new TeaException(_err.getMessage(), _err);
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		}
	}


    public void sendMessage1(String userId,String robotCode,  List<TQuestionAnswer> list,String content,String cardUuid,PageBean pageBean) throws Exception {

	    if(cardUuid==null){
            cardUuid= MD5Util.getMD5Str(userId+System.currentTimeMillis());
        }

        com.aliyun.dingtalkim_1_0.Client client = createClient2();
        SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
        sendInteractiveCardHeaders.xAcsDingtalkAccessToken = getAccessToken();
        SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions cardOptions = new SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions()
                .setSupportForward(true);

        Map<String,String> msgParams=new HashMap<>();

        msgParams.put("text","请问您是要咨询的什么内容呢");
        msgParams.put("content",content);
        msgParams.put("cardUuid",cardUuid);
        msgParams.put("page",pageBean.getPage()+"");
        msgParams.put("next",((pageBean.getPage()-1)*pageBean.getSize()<pageBean.getTotal())+"");
        for(int i=0;i<list.size();i++){
            TQuestionAnswer item = list.get(i);
            msgParams.put("actionTitle"+(i+1),item.getQuestionName());
            msgParams.put("actionURL"+(i+1),"dtmd://dingtalkclient/sendMessage?content="+URLEncoder.encode(item.getQuestionName(),"utf-8"));

        }

        Map<String,String> privateMsgParams=new HashMap<>();
        privateMsgParams.put("content",content);
        privateMsgParams.put("cardUuid",cardUuid);
        privateMsgParams.put("page",pageBean.getPage()+"");
        privateMsgParams.put("next",((pageBean.getPage()-1)*pageBean.getSize()<pageBean.getTotal())+"");
        PrivateDataValue privateDataValueKey = new PrivateDataValue()
                .setCardParamMap(privateMsgParams);

        java.util.Map<String, PrivateDataValue> privateData = TeaConverter.buildMap(
                new TeaPair("privateDataValueKey", privateDataValueKey)
        );



        String s = JSON.toJSONString(msgParams);

        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                .setCardParamMap(msgParams);

        SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                .setCardTemplateId(CardTemplateId)

                .setReceiverUserIdList(java.util.Arrays.asList(
                        userId
                ))
                //每个卡片的id都要不一样
                .setOutTrackId(cardUuid)
                .setRobotCode(robotCode)
                .setCallbackRouteKey("bbb")
                .setConversationType(ConversationType)
                .setCardData(cardData)
                .setPrivateData(privateData)
//                .setChatBotId("123")
//                .setUserIdType(1)
//                .setAtOpenIds(atOpenIds)
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


    public void updateCard(String userId,String robotCode,  List<TQuestionAnswer> list,String content,String cardUuid,PageBean pageBean) throws Exception {
            if(cardUuid==null){
                return;
            }

            com.aliyun.dingtalkim_1_0.Client client = createClient2();
            UpdateInteractiveCardHeaders updateInteractiveCardHeaders = new UpdateInteractiveCardHeaders();
            updateInteractiveCardHeaders.xAcsDingtalkAccessToken =getAccessToken();
            UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions cardOptions = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions()
                    .setUpdateCardDataByKey(false)
                    .setUpdatePrivateDataByKey(false);


            Map<String,String> msgParams=new HashMap<>();
            msgParams.put("text","请问您是要咨询的什么内容呢");

            msgParams.put("content",content);
            msgParams.put("cardUuid",cardUuid);
            msgParams.put("page",pageBean.getPage()+"");
            msgParams.put("next",((pageBean.getPage()-1)*pageBean.getSize()<pageBean.getTotal())+"");
            for(int i=0;i<list.size();i++){
                TQuestionAnswer item = list.get(i);
                msgParams.put("actionTitle"+(i+1),item.getQuestionName());
                msgParams.put("actionURL"+(i+1),"dtmd://dingtalkclient/sendMessage?content="+URLEncoder.encode(item.getQuestionName(),"utf-8"));

            }


            String s = JSON.toJSONString(msgParams);

            UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData cardData = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData()
                    .setCardParamMap(msgParams);
//                    .setCardMediaIdParamMap(cardDataCardMediaIdParamMap);
            UpdateInteractiveCardRequest updateInteractiveCardRequest = new UpdateInteractiveCardRequest()
                    .setOutTrackId(MD5Util.getMD5Str(userId+System.currentTimeMillis()))
                    .setCardData(cardData)
//                    .setPrivateData(privateData)
//                    .setUserIdType(1)
                    .setCardOptions(cardOptions);
            try {
                client.updateInteractiveCardWithOptions(updateInteractiveCardRequest, updateInteractiveCardHeaders, new RuntimeOptions());
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





	public void sendMessageNoQuestion(String userId,String robotCode) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(
						userId
				))
				.setMsgKey("sampleText")
				.setMsgParam("{\"content\": \""+noQuestionStr+"\"}");
		try {
			BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
			System.out.println(JSON.toJSONString(batchSendOTOResponse.getBody()));
		} catch (TeaException err) {
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		} catch (Exception _err) {
			TeaException err = new TeaException(_err.getMessage(), _err);
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		}
	}



    public void sendMessageGPT(String userId,String robotCode,  String result) throws Exception {
        com.aliyun.dingtalkrobot_1_0.Client client = createClient();
        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
        batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();

        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
                .setRobotCode(robotCode)
                .setUserIds(java.util.Arrays.asList(
                        userId
                ))
                .setMsgKey("sampleText")
                .setMsgParam("{\"content\": \""+result+"\"}");
        try {
            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.code);
                System.out.println(err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.code);
                System.out.println(err.message);
            }
        }
    }

	public void sendMessageUnique(String userId,String robotCode,   List<TQuestionAnswer> list) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();

        TQuestionAnswer tQuestionAnswer = list.get(0);
        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(
						userId
				))
				.setMsgKey("sampleText")
				.setMsgParam("{\"content\": \""+tQuestionAnswer.getAnswer()+"\"}");
		try {
			BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
		} catch (TeaException err) {
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		} catch (Exception _err) {
			TeaException err = new TeaException(_err.getMessage(), _err);
			if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
				// err 中含有 code 和 message 属性，可帮助开发定位问题
				System.out.println(err.code);
				System.out.println(err.message);
			}
		}
	}

	public String getAccessToken() throws Exception {
		/**
		 * AgentId
		 * 12539002
		 * AppKey
		 * dingpe3gzuwesjtdhx7i
		 * AppSecret
		 * tnUw33j5KwpAXmew5Q-IsFTmnIDhqUsdCMwFFHSFirsQoYN_PkgHOv9SqMlPLJlz
		 */
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(AppKey);
		request.setAppsecret(AppSecret);
		request.setHttpMethod("GET");
		OapiGettokenResponse response = client.execute(request);
		return response.getAccessToken();
	}




}
