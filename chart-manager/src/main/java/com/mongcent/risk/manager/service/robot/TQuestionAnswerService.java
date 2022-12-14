package com.mongcent.risk.manager.service.robot;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongcent.core.commons.constant.ApiResult;
import com.mongcent.risk.manager.entity.TCategory;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.mapper.TQuestionAnswerMapper;
import com.mongcent.risk.manager.service.es.EsService;
import com.mongcent.risk.manager.util.MD5Util;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;



import static com.mongcent.risk.common.RobotConstants.*;

@Service
@DS("robot")
public class TQuestionAnswerService {

    @Autowired
    TQuestionAnswerMapper tQuestionAnswerMapper;


    @Autowired
    TCategoryService tCategoryService;



    @Autowired
    TRobotService tRobotService;


    @Autowired
    TRobotCategoryService tRobotCategoryService;


    @Autowired
    TQaDetailService tQaDetailService;


    @Autowired
    EsService esService;





    private  static Integer maxCategoryLevel=5;

    private  static Double minEqualLine=0.92;

    private  static Double passLine=0.42;


    private static Logger LOGGER = LoggerFactory.getLogger(TQuestionAnswerService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public Integer add(TQuestionAnswer tQuestionAnswer) {

        int count = tQuestionAnswerMapper.insert(tQuestionAnswer);
        return count;

    }


    public void addAll(List<TQuestionAnswer> list) {

         tQuestionAnswerMapper.insertAll(list);


    }


    public Integer update(TQuestionAnswer tQuestionAnswer) {

        int count = tQuestionAnswerMapper.updateByPrimaryKeySelective(tQuestionAnswer);
        return count;


    }


    public Integer delete(Integer id) {

        int count = tQuestionAnswerMapper.deleteByPrimaryKey(id);
        return count;


    }

    public void  getList(PageBean<TQuestionAnswer> pageBean,
                                          Integer categoryId,String keyword,Integer status){
        int index=(pageBean.getPage() - 1) * pageBean.getSize();
        List<TQuestionAnswer> list = tQuestionAnswerMapper.selectByPage(categoryId,keyword,status,index,pageBean.getSize());

        int count = tQuestionAnswerMapper.getCount(categoryId,keyword,status,index,pageBean.getSize());

        pageBean.setList(list);
        pageBean.setTotal(count);


    }


    /**
     * ???????????????????????????????????????????????????
     * @param tCategoryList
     * @param categoryLine
     * @return
     */
    private Integer checkCategory(List<TCategory> tCategoryList,String categoryLine){
        if(tCategoryList==null || tCategoryList.size()==0 || categoryLine==null || "".equals( categoryLine.trim())){
            return -1;
        }

        String category = categoryLine.substring(0, categoryLine.indexOf("/") < 0?categoryLine.length():categoryLine.indexOf("/"));
        if(category==""){
            category=categoryLine;
        }

        TCategory tCategory=null;
        for(TCategory item:tCategoryList){
            if(item.getCategory()!=null && item.getCategory().equals(category)){
                tCategory=item;
                break;
            }
        }

        String substring2 = categoryLine.substring( (categoryLine.indexOf("/") < 0?categoryLine.length():categoryLine.indexOf("/")+1), categoryLine.length());
        if(tCategory!=null){
            if(substring2!=null &&  !"".equals(substring2)){
                if(tCategory.getChildren().size()>0){
                    return checkCategory(tCategory.getChildren(),substring2);
                }else{
                    return -1;
                }
            }else{
                return tCategory.getId();
            }
        }
        return -1;
    }


    /**
     * ????????????????????????
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public  String  importQuestionAnswers(MultipartFile file) {
        String result = "?????????????????????";
        List<TQuestionAnswer> list = new ArrayList<>();
        List<HashMap<String,Object>> esList = new ArrayList<>();

        List<TCategory> categoryTree = tCategoryService.getTree();
        try {

            Workbook  workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getPhysicalNumberOfRows();
            //??????????????????????????????????????????????????????(i?????????????????????????????????????????????)
            for (int i = 2; i < rowNum; i++) {
                Row row = sheet.getRow(i);    //??????????????????i????????????

                //?????????????????????i??????????????????????????????(j????????????????????????????????????????????????
                String categoryLine = row.getCell(0).getStringCellValue();
                String[] categoryLineSplit = categoryLine.split("\\/");
                //????????????????????????5??????
                if (categoryLineSplit.length >= maxCategoryLevel) {
                    return "??????????????????5???";
                }

                //1.????????????????????????????????????
                Integer categoryId = checkCategory(categoryTree, categoryLine);
                if (categoryId < 0) {
                    return categoryLine + "  ???????????????";
                }
                //??????????????????
                String questionName = row.getCell(1).getStringCellValue();
                List<String> questionList = new ArrayList<>();

                //??????????????????????????????????????????????????????list
                if (row.getCell(2) != null) {
                    String questionNames = row.getCell(2).getStringCellValue();
                    String[] questionsSplit = questionNames.split("\n");
                    for (String question : questionsSplit) {
                        if("".equals(question)){
                            continue;
                        }
                        questionList.add(question);

                    }
                }

//                HashMap<String, String> q = new HashMap<>();
//                q.put(questionStr, questionName);
//                questionList.add(q);


                //?????????????????????
                String answer = "";
                if (row.getCell(3) != null) {
                    answer = row.getCell(3).getStringCellValue();
                }

                long timestamp = System.currentTimeMillis();
                String id = MD5Util.getMD5Str(questionName + timestamp);




                TQuestionAnswer qa = new TQuestionAnswer(id, questionName, JSON.toJSONString(questionList), answer, categoryId, new Date(timestamp));


                //??????????????????es???????????????
                HashMap<String, Object> esMap=new HashMap<>();
                esMap.put(questionStr, questionName);
                esMap.put(similarNames, questionList);
                esMap.put(qidStr,id);
                esList.add(esMap);

                //??????mysql
                add(qa);
//                list.add(qa);

            }

//            addAll(list);
//            int a=1/0;


            System.out.println(11);




            //3.????????????????????????????????????es???  ??????id ??????????????????
            for (HashMap<String, Object> esMap : esList) {
                esService.add(esMap);

            }


        } catch (Exception e) {
            e.printStackTrace();
            return "??????????????????";
        }


        return result;
    }


    public  List<TQuestionAnswer> searchQuestionAnswers(String keyword, PageBean pageBean) {


        JSONObject json = esService.search(keyword,pageBean);

        /**
         {
         "took" : 3,
         "timed_out" : false,
         "hits" : {
         "max_score" : 30.116997,
         "hits" : [
         {
         "_source" : {
         "similar_names" : [
         "?????????????????????????????????"
         ],
         "question" : "???????????????????????????????????????????????????????????????????????????",
         "q_id" : "9bf6aa34294d2806cb429f900562e979"
         }
         },
         map("hits").map("hits") for  map("_source" ).

         */

        Integer total=json.getJSONObject("hits").getJSONObject("total").getIntValue("value");
        pageBean.setTotal(total);
        JSONArray hits = json.getJSONObject("hits").getJSONArray("hits");
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);

        //??????es????????????????????????id??????
        List<TQuestionAnswer> questionList=new ArrayList<>();
        List<String> idList=new ArrayList<>();
        for(int i=0;i<hits.size();i++){
            JSONObject _source = hits.getJSONObject(i).getJSONObject("_source");
            Double _score = hits.getJSONObject(i).getDouble("_score");
            String question = _source.getString(questionStr);
            String id = _source.getString(qidStr);
            JSONArray similarNameList = _source.getJSONArray(similarNames);

            double score = service.score(keyword, question);
            for(int j=0;j<similarNameList.size();j++){
                String similar = similarNameList.getString(j);
                score = Math.max(service.score(keyword, similar),score);
            }

            questionList.add(new TQuestionAnswer(id,question ,null,_score));
            idList.add(id);
            if(score>=minEqualLine ){
                break;
            }

//            if( score<=passLine){
//                continue;
//            }
        }

        //??????id ????????????mysql????????????????????????
        if(idList.size()>0){
            List<TQuestionAnswer> tQuestionAnswers = tQuestionAnswerMapper.selectByIdList(idList);
            if(questionList.size()==1){
                TQuestionAnswer tQuestionAnswer = questionList.get(0);
                TQuestionAnswer tQuestionAnswer1 = tQuestionAnswers.get(0);
                tQuestionAnswer.setAnswer(tQuestionAnswer1.getAnswer());
            }


        }


        return questionList;
    }
}
