package com.zj.demo.service;

import com.zj.demo.pojo.QA;

import java.util.List;

public interface QAService {
    int insertQuestion(QA qa);

    List<QA> selectAllByViews();
}
