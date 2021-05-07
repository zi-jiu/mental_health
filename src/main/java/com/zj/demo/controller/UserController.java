package com.zj.demo.controller;


import com.alibaba.druid.util.StringUtils;
import com.zj.demo.bo.UserBO;
import com.zj.demo.exception.MyException;
import com.zj.demo.pojo.Result;
import com.zj.demo.pojo.Type;
import com.zj.demo.pojo.User;
import com.zj.demo.service.TypeService;
import com.zj.demo.service.UserService;
import com.zj.demo.util.FastDFSClient;
import com.zj.demo.util.FileUtils;
import com.zj.demo.util.MD5Util;
import com.zj.demo.util.SnowFlakeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserController
 * @Author 字九
 * @Date 2021/3/23 11:16
 * @Description
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
//RestController = Controller + RestBody
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 登录
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@ApiParam("用户") User user,HttpServletRequest request) throws MyException {
        System.out.println("进入登录");
       // String username = request.getParameter("username");
       // String password = request.getParameter("password");

        String username = user.getUsername();
        String password = MD5Util.getMD5(user.getPassword());

        System.out.println("name:"+username);
        System.out.println("password:"+password);

        //如果用户名或密码为空，返回用户名或密码不能为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new MyException(0,"用户名或密码不能为空");
        }

        //如果用户名或密码不正确，返回用户名或密码不正确
        User newUser = userService.selectUser(username, password);

        if(newUser==null){
            System.out.println("用户名或密码不正确");
            throw new MyException(0,"用户名或密码不正确");
        }

        //设置session
        request.getSession().setAttribute("user",newUser);

        System.out.println(request.getSession().getAttribute("user").toString());

        //System.out.println(request.getSession().getAttribute("login_name"));
        //System.out.println(request.getSession().getAttribute("pass_word"));
        System.out.println("成功");
        return Result.success();

    }


    /**
     * 注册
     */
    //注册---验证用户名密码是否存在,如果不存在就注册
    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public Result register(@ApiParam("用户")User user){
        String username = user.getUsername();
        String password = MD5Util.getMD5(user.getPassword());
        //如果用户名或密码为空，返回用户名或密码不能为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new MyException(0,"用户名或密码不能为空");
        }
        //如果用户名已存在
        String res = userService.selectUserNameisEmpty(username);
        if(res.equals("0")){
            throw new MyException(0,"用户名已存在");
        }

        //进行注册
        //雪花算法生成全局唯一id
        long snowFlakeId = SnowFlakeUtil.getSnowflakeId();
        String id = String.valueOf(snowFlakeId);
        System.out.println(id);

        userService.insertUserInfo(id,username, password);
        return Result.success();

    }

    /**
     * 完善用户信息
     */
    @ApiOperation("完善用户信息")
    @PostMapping(value = "/addUserExInfo")
    public Result addUserExInfo(@ApiParam("用户")User user){
        int res = userService.updateByPrimaryKey(user);
        if(res==0) Result.error("完善信息有误");
        return Result.success();
    }

    /**
     * 专家认证（两种方式：申请-->通过 或者 后台直接改表）
     * 1.t_user表中is_expert设置为1
     * 2.擅长领域
     */

    /**
     * 上传头像
     */
    @ApiOperation("上传头像")
    @PostMapping(value = "/updateUserFaceImage")
    public Result updateUserFaceImage(@ApiParam("用户id和base64头像")@RequestBody UserBO userBO) throws Exception {
        //获取前端传过来的base64的字符串，然后转为文件对象在进行上传
        String base64Data = userBO.getFaceData();
        String userFacePath = "D:\\upload\\2021\\"+userBO.getUserId()+"userFaceBase64.png";
        //调用FileUtils 类中的方法将base64 字符串转为文件对象
        FileUtils.base64ToFile(userFacePath, base64Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        //获取fastDFS上传图片的路径
        String url = fastDFSClient.uploadBase64(multipartFile);
        System.out.println(url);
        String thump = "_150x150.";
        String[] arr = url.split("\\.");
        String thumpImgUrl = arr[0]+thump+arr[1];
        String pre_str = "http://39.106.39.221/zj/";
//      String bigFace = "dssdklsdjsdj3498458.png";
//      String thumpFace = "dssdklsdjsdj3498458_150x150.png";
        //更新用户头像
        User user = new User();
        user.setId(userBO.getUserId());
        user.setFaceImage(pre_str+thumpImgUrl);
        user.setFaceImageBig(pre_str+url);
        int res = userService.updateUserImage(user);
        if(res == 0) return Result.error("图片上传失败");
        return Result.success();
    }

    /**
     * 展示单个用户的详细信息
     */
    @ApiOperation("展示单个用户的详细信息")
    @RequestMapping(method = RequestMethod.GET,value = "/selectUserInfoByID")
    public Map selectUserInfoByID(ModelMap modelMap,@ApiParam("用户id") String id) {
        if(StringUtils.isEmpty(id)){
            throw new MyException(0,"用户ID不能为空");
        }
        User user = userService.selectUserInfoByID(id);
        if(user == null){
            throw new MyException(0,"用户ID不正确");
        }
        Map<String,Object> map = new HashMap<>();
        int typeId = user.getTypeId();
        Type type = typeService.selectByPrimaryKey(typeId);
        String typeName = type.getName();
        map.put("user",user);
        map.put("typeName",typeName);
        return map;
    }









}
