package com.mongcent.es.controller;

import com.alibaba.fastjson.JSONObject;

import com.mongcent.core.commons.constant.ApiResult;
import com.mongcent.es.request.ESActuator;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author zl
 * @Date 2019/7/17
 **/
@RestController
public class ESController {
    @Resource
    private ESActuator esActuator;

    @ApiOperation("访问ES")
    @GetMapping("v1/statistics/es/{name}")
    public ApiResult queryESByName(@PathVariable String name, @RequestParam Map<String, String> paramMap) {
        JSONObject jsonObject = esActuator.execute(name, paramMap);
        return ApiResult.success(jsonObject);
    }
}
