package com.mongcent.risk.manager.service.robot;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongcent.risk.manager.entity.TQaDetail;
import com.mongcent.risk.manager.entity.TQuestionAnswer;
import com.mongcent.risk.manager.entity.vo.PageBean;
import com.mongcent.risk.manager.mapper.TQaDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("robot")
public class TQaDetailService {

    @Autowired
    TQaDetailMapper tQaDetailMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(TQaDetailService.class);
    private static Gson gson = new GsonBuilder()
            .create();

    public Integer add(TQaDetail tQaDetail) {

        int count = tQaDetailMapper.insert(tQaDetail);
        return count;

    }


    public Integer update(TQaDetail tQaDetail) {

        int count = tQaDetailMapper.updateByPrimaryKeySelective(tQaDetail);
        return count;


    }


    public Integer delete(Integer id) {

        int count = tQaDetailMapper.deleteByPrimaryKey(id);
        return count;


    }

    public void  getList(PageBean<TQuestionAnswer> pageBean,
                         String userName,String question,Integer answerStatus){
        int index=(pageBean.getPage() - 1) * pageBean.getSize();
        List<TQuestionAnswer> list = tQaDetailMapper.selectByPage(answerStatus,userName,question,index,pageBean.getSize());

        int count = tQaDetailMapper.getCount(answerStatus,userName,question,index,pageBean.getSize());

        pageBean.setList(list);
        pageBean.setTotal(count);


    }


}
