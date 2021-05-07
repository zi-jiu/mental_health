package com.zj.demo.service;

import com.zj.demo.mapper.TypeMapper;
import com.zj.demo.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TypeServiceImpl
 * @Author 字九
 * @Date 2021/3/26 17:28
 * @Description
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type selectByPrimaryKey(int id) {
        return typeMapper.selectByPrimaryKey(id);
    }
}
