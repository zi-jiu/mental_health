package com.zj.demo.netty;


import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserChannelRel
 * @Author 字九
 * @Date 2021/3/29 19:58
 * @Description 用户Id和Channel的关联关系处理
 **/
public class UserChannelRel {

    private static Map<String, Channel> manage = new HashMap<>();

    public static void put(String senderId,Channel channel) {
        manage.put(senderId,channel);
    }

    public static Channel get(String senderId) {
        return manage.get(senderId);
    }

    public static void output(){
        for (Map.Entry<String, Channel> entry : manage.entrySet()) {
            System.out.println("userId = "+entry.getKey());
            System.out.println("channelId = "+entry.getValue().id().asLongText());
        }
    }


}
