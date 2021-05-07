package com.zj.demo.mapper;

import com.zj.demo.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    //User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUser(String username,String password);

    String selectUserNameisEmpty(@Param("username") String username);

    void insertUserInfo(String id,String username, String password);

    int updateUserImage(User user);

    User selectUserInfoByID(String id);
}