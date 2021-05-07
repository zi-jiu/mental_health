package com.zj.demo.mapper;

import com.zj.demo.pojo.Law;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawMapper {
    int deleteByPrimaryKey(String id);

    int insert(Law record);

    int insertSelective(Law record);

    Law selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Law record);

    int updateByPrimaryKey(Law record);

    List<Law> selectAllByViewCount();

}