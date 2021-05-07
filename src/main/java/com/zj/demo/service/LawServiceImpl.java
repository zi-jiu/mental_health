package com.zj.demo.service;

import com.zj.demo.mapper.LawMapper;
import com.zj.demo.pojo.Law;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LawServiceImpl
 * @Author 字九
 * @Date 2021/3/30 16:44
 * @Description
 **/
@Service
public class LawServiceImpl implements LawService {

    @Autowired
    private LawMapper lawMapper;

    @Override
    public List<Law> selectAllByViewCount() {
        return lawMapper.selectAllByViewCount();
    }

    @Override
    public Law selectByPrimaryKey(String id) {
        return lawMapper.selectByPrimaryKey(id);
    }


}
