package com.mongcent.risk.manager.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongcent.core.commons.constant.ApiResult;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.service.gpt.GPTService;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping(value = "gpt/")
public class ChatGPTController {



    @Autowired
    private GPTService gptService;


    @RequestMapping(value = "/qa/search", method = RequestMethod.POST)
    public ApiResult qaSearch(String keyword, Integer page) {

        if (keyword==null || keyword.isEmpty()) {
            return ApiResult.success("");
        }

        String result="";

        result = gptService.getgpt(gptService.token, keyword);

//        try {
//            result = gptService.getgpt(token, keyword);
//        }catch (Exception e){
//            result=gptService.getgptByPhp(keyword);
//        }


        if(result.length()==0){
            return ApiResult.success("亲，您问的问题可以换个方式问哦");
        }

        return ApiResult.success(result);


    }
}
