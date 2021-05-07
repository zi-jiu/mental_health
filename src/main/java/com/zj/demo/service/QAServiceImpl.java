package com.zj.demo.service;

import com.zj.demo.mapper.QAMapper;
import com.zj.demo.pojo.QA;
import com.zj.demo.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName QAServiceImpl
 * @Author 字九
 * @Date 2021/3/24 11:10
 * @Description
 **/
@Service
public class QAServiceImpl implements QAService {

    @Autowired
    private QAMapper qaMapper;

    @Override
    public int insertQuestion(QA qa) {
        Date now = new Date();
        qa.setUpdateTime(now);
        //雪花算法生成全局唯一id
        long snowFlakeId = SnowFlakeUtil.getSnowflakeId();
        String id = String.valueOf(snowFlakeId);
        System.out.println(id);
        qa.setId(id);
        qa.setIsDel(0);
        qa.setViews(100);
        return qaMapper.insertQuestion(qa);
    }

    @Override
    public List<QA> selectAllByViews() {
        return qaMapper.selectAllByViews();
    }
}
