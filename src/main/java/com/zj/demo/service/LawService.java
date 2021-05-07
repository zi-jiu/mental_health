package com.zj.demo.service;

import com.zj.demo.pojo.Law;

import java.util.List;

/**
 * @ClassName LawService
 * @Author 字九
 * @Date 2021/3/30 16:44
 * @Description
 **/
public interface LawService {

    List<Law> selectAllByViewCount();

    Law selectByPrimaryKey(String id);
}
