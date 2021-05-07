package com.zj.demo.service;

import com.zj.demo.enums.MsgSignFlagEnum;
import com.zj.demo.mapper.ChatMsgMapper;
import com.zj.demo.mapper.UserMapper;
import com.zj.demo.netty.ChatMsg;
import com.zj.demo.pojo.User;


import com.zj.demo.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @ClassName UserServiceImpl
 * @Author 字九
 * @Date 2021/3/23 13:49
 * @Description
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMsgMapper chatMsgMapper;




    @Override
    public User selectUser(String username, String password) {
        return userMapper.selectUser(username,password);
    }

    @Override
    public String selectUserNameisEmpty(String username) {
        String res = userMapper.selectUserNameisEmpty(username);
        //用户名存在则返回“0”
        if(res!=null){
            return "0";
        }
        //否则返回“1”
        return "1";
    }

    @Override
    public void insertUserInfo(String id,String username, String password) {
        userMapper.insertUserInfo(id,username,password);

    }

    @Override
    public int updateByPrimaryKey(User user) {
        int res = userMapper.updateByPrimaryKey(user);
        return res;
    }

    @Override
    public int updateUserImage(User user) {
        int res = userMapper.updateUserImage(user);
        return res;
    }

    @Override
    public User selectUserInfoByID(String id) {
        return userMapper.selectUserInfoByID(id);
    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.zj.demo.pojo.ChatMsg MsgDB = new com.zj.demo.pojo.ChatMsg();
        //雪花算法生成全局唯一id
        long snowFlakeId = SnowFlakeUtil.getSnowflakeId();
        String msgId = String.valueOf(snowFlakeId);
        System.out.println(msgId);
        MsgDB.setId(msgId);
        MsgDB.setAcceptUserId(chatMsg.getReceiverId());
        MsgDB.setSendUserId(chatMsg.getSenderId());
        MsgDB.setCreateTime(new Date());
        //保存消息时为未签收
        MsgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        MsgDB.setMsg(chatMsg.getMsg());

        int insert = chatMsgMapper.insert(MsgDB);
        if(insert == 1){
            return msgId;
        }else{
            return "-1";
        }
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.updateMsgSigned(msgIdList);
    }
}
