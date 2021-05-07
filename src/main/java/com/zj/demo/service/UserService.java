package com.zj.demo.service;


import com.zj.demo.netty.ChatMsg;
import com.zj.demo.pojo.User;

import java.util.List;

public interface UserService {

    User selectUser(String username,String password);

    String selectUserNameisEmpty(String username);

    void insertUserInfo(String id,String username, String password);

    int updateByPrimaryKey(User user);

    int updateUserImage(User user);

    User selectUserInfoByID(String id);

    //保存消息
    String saveMsg(ChatMsg chatMsg);

    //更新信息已签收
    void updateMsgSigned(List<String> msgIdList);



}
