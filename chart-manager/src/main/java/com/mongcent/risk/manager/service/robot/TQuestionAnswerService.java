package com.mongcent.risk.manager.service.robot;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
     * 验证批量导入问答数据的类目是否存在
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
     * 批量导入问答数据
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public  String  importQuestionAnswers(MultipartFile file) {
        String result = "上传文件成功！";
        List<TQuestionAnswer> list = new ArrayList<>();
        List<HashMap<String,Object>> esList = new ArrayList<>();

        List<TCategory> categoryTree = tCategoryService.getTree();
        try {

            Workbook  workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getPhysicalNumberOfRows();
            //循环遍历表格的每一行，获取每一行的值(i的初始值决定从表格的第几行开始)
            for (int i = 2; i < rowNum; i++) {
                Row row = sheet.getRow(i);    //获取表格中第i行的数据

                //循环遍历表格第i行的每一个单元格的值(j的初始值决定从表格的第几列开始）
                String categoryLine = row.getCell(0).getStringCellValue();
                String[] categoryLineSplit = categoryLine.split("\\/");
                //类目层级不能超过5层，
                if (categoryLineSplit.length >= maxCategoryLevel) {
                    return "类目不能超过5层";
                }

                //1.验证类目有没有全部创建好
                Integer categoryId = checkCategory(categoryTree, categoryLine);
                if (categoryId < 0) {
                    return categoryLine + "  类目不存在";
                }
                //获取问题名称
                String questionName = row.getCell(1).getStringCellValue();
                List<String> questionList = new ArrayList<>();

                //获取相似问答列表字符串，并把它处理成list
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


                //获取答案字符串
                String answer = "";
                if (row.getCell(3) != null) {
                    answer = row.getCell(3).getStringCellValue();
                }

                long timestamp = System.currentTimeMillis();
                String id = MD5Util.getMD5Str(questionName + timestamp);




                TQuestionAnswer qa = new TQuestionAnswer(id, questionName, JSON.toJSONString(questionList), answer, categoryId, new Date(timestamp));


                //添加到要插入es的数据列表
                HashMap<String, Object> esMap=new HashMap<>();
                esMap.put(questionStr, questionName);
                esMap.put(similarNames, questionList);
                esMap.put(qidStr,id);
                esList.add(esMap);

                //插入mysql
                add(qa);
//                list.add(qa);

            }

//            addAll(list);
//            int a=1/0;


            System.out.println(11);




            //3.第二步完成后，导入数据到es中  包含id 和所有的问题
            for (HashMap<String, Object> esMap : esList) {
                esService.add(esMap);

            }


        } catch (Exception e) {
            e.printStackTrace();
            return "文件解析失败";
        }


        return result;
    }


    public  List<TQuestionAnswer> searchquestionanswers(String keyword, PageBean pageBean) {


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
         "机构经营有效期如何填写"
         ],
         "question" : "营业执照机构的资质是长期，有效期截至时间怎么填写？",
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

        //解析es出来的结果，获取id列表
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

        //通过id 列表获取mysql的数据返回到前端
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
