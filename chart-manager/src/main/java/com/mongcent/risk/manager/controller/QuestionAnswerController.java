package com.mongcent.risk.manager.controller;

import com.mongcent.core.commons.constant.ApiResult;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.service.robot.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RestController
@RequestMapping(value = "v1/")
/**
 *
 * @author zhongli
 * 问答数据操作
 */
public class QuestionAnswerController {


    @Autowired
    TCategoryService tCategoryService;


    @Autowired
    TQuestionAnswerService tQuestionAnswerService;


    @Autowired
    TRobotService tRobotService;


    @Autowired
    TRobotCategoryService tRobotCategoryService;


    @Autowired
    TQaDetailService tQaDetailService;


    private  static Integer maxCategoryLevel=5;
    private  static String noQuestionStr="亲，您问的问题可以换个方式问哦";




	private static Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerController.class);
	 
    
    



	/**
	 * test
	 * @return
	 */
	@RequestMapping(value = "/qa/import", method = RequestMethod.POST)
	public ApiResult getEsStatusErrorList(String message)  {
		List<Map<String, Object>> result = new ArrayList<>();
		try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ApiResult.success(message);
	}




	@PostMapping("/qa/import/uploadFile")
	public ApiResult uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
			return ApiResult.error("上传失败，请选择文件");
		}

		String fileName = file.getOriginalFilename();
		if ( fileName.toLowerCase().endsWith(".xlsx")) {
		   return ApiResult.success(tQuestionAnswerService.importQuestionAnswers(file));
		} else {
			return ApiResult.error("只允许上传xlsx文件");
		}


	}



	@RequestMapping(value = "/qa/search", method = RequestMethod.POST)
    public ApiResult qaSearch(String keyword,Integer page) {

        if (keyword==null || keyword.isEmpty()) {
            return ApiResult.success("");
        }

        //1.查询es 获取相关度分值大于设定的阈值的数据列表
        //2.使用相识度算法在次进行字符串比对，
            // 超过0.95说明就是该问题，
            // 如果没有超过0.95的则推荐超过0.5以上的问题
            // 如果零点五以上的都没有，则返回无相关问题答案的提示
		PageBean pageBean=new PageBean();
		pageBean.setPage(page);
		pageBean.setSize(2);
		List<TQuestionAnswer> result=	tQuestionAnswerService.searchquestionanswers(keyword,pageBean);
        if(result.size()==0){
			return ApiResult.success("亲，您问的问题可以换个方式问哦");
		}

        return ApiResult.success(result);


    }







}
