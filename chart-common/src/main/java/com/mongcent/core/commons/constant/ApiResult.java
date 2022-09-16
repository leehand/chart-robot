package com.mongcent.core.commons.constant;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理API返回结果
 * 
 */
public class ApiResult extends JSONObject {

	private static final long serialVersionUID = -1073411906807304534L;

	/**
	 * 成功信息返回
	 * 
	 * @param data
	 *            成功后需要返回的数据
	 * @return
	 */
	public static ApiResult success(Object data) {
		ApiResult apiResult = new ApiResult();
		apiResult.put(ApiConsts.CODE_KEY, ApiConsts.CODE_SUCCESS);
		apiResult.put(ApiConsts.MSG_KEY, ApiConsts.MSG_OK);
		apiResult.put(ApiConsts.DATA_KEY, data);
		return apiResult;
	}
//
//	/**
//	 * 成功不返回数据
//	 * @return
//	 */
//	public static ApiResult success() {
//		ApiResult apiResult = new ApiResult();
//		apiResult.put(ApiConsts.CODE_KEY, ApiConsts.CODE_SUCCESS);
//		apiResult.put(ApiConsts.MSG_KEY, ApiConsts.MSG_OK);
//		return apiResult;
//	}



	/**
	 * 错误信息返回
	 * 
	 * @param msg
	 *            错误消息
	 * @return
	 */
	public static ApiResult error(Object msg) {
		ApiResult apiResult = new ApiResult();
		apiResult.put(ApiConsts.CODE_KEY, ApiConsts.CODE_SERVER_ERR);
		apiResult.put(ApiConsts.MSG_KEY, msg);
		return apiResult;
	}

	/**
	 * 错误信息返回
	 * 
	 * @param msg
	 *            错误消息
	 * @return
	 */
	public static ApiResult error(Integer code, Object msg) {
		ApiResult apiResult = new ApiResult();
		apiResult.put(ApiConsts.CODE_KEY, code);
		apiResult.put(ApiConsts.MSG_KEY, msg);
		return apiResult;
	}

	/**
	 * ajax请求完成
	 * 
	 * @return
	 */
	public static ApiResult done(Integer code, Object msg, Object data) {
		ApiResult apiResult = new ApiResult();
		apiResult.put(ApiConsts.CODE_KEY, code);
		apiResult.put(ApiConsts.MSG_KEY, msg);
		apiResult.put(ApiConsts.DATA_KEY, data);
		return apiResult;
	}
}
