package com.mongcent.core.commons.base;

import com.mongcent.core.commons.constant.ApiResult;

/**
 * @Description
 * @Author zl
 * @Date 2018/11/22
 **/
public class BaseController {
    /***失败返回的信息*/
    protected final String updateFailMsg = "修改失败，请检查是否存在";
    protected final String addFailMsg = "增加失败！";
    protected final String deleteFailMsg = "删除失败，请检查是否存在";

    /**
     * 封装返回的数据格式
     * 增加、修改、删除这些不需要返回数据的接口用
     * 查询条件不用
     *
     * @param success
     * @param object  返回的数据
     * @return
     */
    protected ApiResult result(boolean success, Object object) {
        return result(success, object, null);
    }

    protected ApiResult result(boolean success, Object object, String errMsg) {
        if (success) {
            return ApiResult.success(object);
        }
        return ApiResult.error(errMsg);
    }

//    protected ApiResult result(boolean success) {
//        return result(success, null,"操作失败");
//    }
}
