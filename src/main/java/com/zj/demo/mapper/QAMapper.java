package com.zj.demo.mapper;

import com.zj.demo.pojo.QA;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QAMapper {
    int deleteByPrimaryKey(String id);

    int insertQuestion(QA record);

    int insertSelective(QA record);

    QA selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QA record);

    int updateByPrimaryKey(QA record);

    List<QA> selectAllByViews();
}