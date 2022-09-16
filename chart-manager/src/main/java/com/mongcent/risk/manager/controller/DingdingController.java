package com.mongcent.risk.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mongcent.core.commons.constant.ApiResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "v2/")
/**
 *
 * @author zhongli
 * dingding机器人api
 */
public class DingdingController {

//yuanxin
//	public static final String 		 AgentId= "12539002";
//	public static final String 		 AppKey= "dingpe3gzuwesjtdhx7i";
//	public static final String 		 AppSecret= "tnUw33j5KwpAXmew5Q-IsFTmnIDhqUsdCMwFFHSFirsQoYN_PkgHOv9SqMlPLJlz";


	//leehand

	public static final String 		 AgentId= "14388001";
	public static final String 		 AppKey= "dinglfrbyyxwkl0qevyf";
	public static final String 		 AppSecret= "1MhJBFxGZgEW17_x3JgKft0oxSYpQ7-WE2DMeqv9ywtekWDB-JF-Gltu9VXEyaGM";


	private static Logger LOGGER = LoggerFactory.getLogger(DingdingController.class);



	@RequestMapping(value = "/dingding/robots", method = RequestMethod.POST)
	public String helloRobots(@RequestBody(required = false) JSONObject json) throws Exception {
		System.out.println(JSON.toJSONString(json));
		/**
		 * {
		 * 	"conversationId": "cidSyHSJ8oHvD/0+02vIHaLLz6gxlzI/Eyfoe6LK0sSMOc=",
		 * 	"chatbotCorpId": "ding8a065b6a880908d0",
		 * 	"chatbotUserId": "$:LWCP_v1:$TLyARKYLArpvNbHYYmUxOUO36yfoXGK1",
		 * 	"msgId": "msgcLFQXp+guDkTB/5BWruw3Q==",
		 * 	"senderNick": "钟理",
		 * 	"isAdmin": true,
		 * 	"senderStaffId": "1618832891745870",
		 * 	"sessionWebhookExpiredTime": 1659527937100,
		 * 	"createAt": 1659522536999,
		 * 	"senderCorpId": "ding8a065b6a880908d0",
		 * 	"conversationType": "1",
		 * 	"senderId": "$:LWCP_v1:$0en4DIRmWpRCZegbp1RWAQ==",
		 * 	"sessionWebhook": "https://oapi.dingtalk.com/robot/sendBySession?session=adf2eccade9ea7599a1869b69daad7a6",
		 * 	"text": {
		 * 		"content": "发票"
		 *        },
		 * 	"robotCode": "dingpe3gzuwesjtdhx7i",
		 * 	"msgtype": "text"
		 * }
		 */
		json.getJSONObject("text");
		String content = json.getJSONObject("text")
				.getString("content");
		String userId = json.get("senderStaffId").toString();
		String robotCode = json.get("robotCode").toString();
		String page = json.get("robotCode").toString();
		//{"text":{"content"："发票"}，"senderStaffId":"2132"}
		System.out.println(content);
		if (content.equals("发票")) {
			sendMessage1(userId,robotCode);
		} else if (content.equals("发票要普票还是专票")) {
			sendMessage2(userId,robotCode);
		} else if (content.equals("发票内容开什么")) {
			sendMessage3(userId,robotCode);
		} else if (content.equals("差旅住宿普通发票可以报销吗")) {
			sendMessage4(userId,robotCode);
		}
		return null;
	}

	public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
		Config config = new Config();
		config.protocol = "https";
		config.regionId = "central";
		return new com.aliyun.dingtalkrobot_1_0.Client(config);
	}

	public void sendMessage1(String userId,String robotCode) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(userId))
				.setMsgKey("sampleActionCard3")
				.setMsgParam("{\n" +
						"        \"title\": \"发票\",\n" +
						"        \"text\": \"请问您是要咨询发票的什么内容呢\",\n" +
						"        \"actionTitle1\": \"发票要普票还是专票\",\n" +
						"        \"actionURL1\": \"dtmd://dingtalkclient/sendMessage?content=%E5%8F%91%E7%A5%A8%E8%A6%81%E6%99%AE%E7%A5%A8%E8%BF%98%E6%98%AF%E4%B8%93%E7%A5%A8\",\n" +
						"        \"actionTitle2\": \"发票内容开什么\",\n" +
						"        \"actionURL2\": \"dtmd://dingtalkclient/sendMessage?content=%E5%8F%91%E7%A5%A8%E5%86%85%E5%AE%B9%E5%BC%80%E4%BB%80%E4%B9%88\",\n" +
						"        \"actionTitle3\": \"差旅住宿普通发票可以报销吗\",\n" +
						"        \"actionURL3\": \"dtmd://dingtalkclient/sendMessage?content=%E5%B7%AE%E6%97%85%E4%BD%8F%E5%AE%BF%E6%99%AE%E9%80%9A%E5%8F%91%E7%A5%A8%E5%8F%AF%E4%BB%A5%E6%8A%A5%E9%94%80%E5%90%97\"\n" +
						"    }");
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

	public void sendMessage2(String userId,String robotCode) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(
						userId
				))
				.setMsgKey("sampleText")
				.setMsgParam("{\"content\": \"发票需要普票\"}");
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

	public void sendMessage3(String userId,String robotCode) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(
						userId
				))
				.setMsgKey("sampleMarkdown")
				.setMsgParam("{\n" +
						"        \"title\": \"发票内容\",\n" +
						"        \"text\": \"发票内容一般包括:票头、字轨号码、联次及用途、客户名称、银行开户账号、商(产)品名称或经营项目、计量单位、数量、单价、金额,以及大小写金额、经手人、单位印章、开票日期等。实行增值税的单位所使用的增值税专用发票还应有税种、税率、税额等内容。\"\n" +
						"    }");
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

	public void sendMessage4(String userId,String robotCode) throws Exception {
		com.aliyun.dingtalkrobot_1_0.Client client = createClient();
		BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
		batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
		BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
				.setRobotCode(robotCode)
				.setUserIds(java.util.Arrays.asList(
						userId
				))
				.setMsgKey("sampleText")
				.setMsgParam("{\"content\": \"差旅住宿普通发票可以报销\"}");
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
