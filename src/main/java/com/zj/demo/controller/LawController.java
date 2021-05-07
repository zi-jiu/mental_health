package com.zj.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.zj.demo.exception.MyException;
import com.zj.demo.pojo.Law;
import com.zj.demo.service.LawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName LawController
 * @Author 字九
 * @Date 2021/3/30 16:40
 * @Description
 **/
@Api("法律")
@RestController
@RequestMapping("/law")
public class LawController {


    @Autowired
    private LawService lawService;

    /**
     * 展示点击率高的法律
     */
    @ApiOperation("展示点击率高的法律")
    @RequestMapping(value = "/selectAllByViewCount",method = RequestMethod.GET)
    public List<Law> selectAllByViewCount(){
        List<Law> list = lawService.selectAllByViewCount();
        return list;
    }

    /**
     * 查询指定id的法律
     */
    @ApiOperation("查询指定id的法律")
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Law selectByPrimaryKey(@ApiParam("法律id") String id){
        System.out.println("进来了");
        if(StringUtils.isEmpty(id)){
            throw new MyException(0,"查询的法律ID不能为空");
        }
        Law law = lawService.selectByPrimaryKey(id);
        return law;
    }
}
