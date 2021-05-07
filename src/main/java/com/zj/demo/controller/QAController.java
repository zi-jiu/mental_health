package com.zj.demo.controller;

import com.zj.demo.pojo.QA;
import com.zj.demo.pojo.Result;
import com.zj.demo.pojo.User;
import com.zj.demo.service.QAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName QAController
 * @Author 字九
 * @Date 2021/3/24 11:11
 * @Description
 **/
@Api("问题帖子")
@RestController
@RequestMapping("/qa")
public class QAController {

    @Autowired
    private QAService qaService;

    /**
     * 用户发帖子
     */
    @ApiOperation("用户发帖子")
    @PostMapping("/insertQuestion")
    public Result insertQuestion(@ApiParam("问题帖子") QA qa, HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("user");
        String uId = userInfo.getId();
        qa.setUserId(uId);
        int res = qaService.insertQuestion(qa);
        if(res == 0) return Result.error("发帖失败");
        return Result.success();
    }

    /**
     * 展示热点帖子
     */
    @ApiOperation("展示热点帖子")
    @RequestMapping(method = RequestMethod.GET,value = "/selectAllByViews")
    public List<QA> selectAllByViews(){
        List<QA> list = qaService.selectAllByViews();
        return list;
    }





}
