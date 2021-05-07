package com.zj.demo.netty;



import java.io.Serializable;

/**
 * @ClassName DataContent
 * @Author 字九
 * @Date 2021/3/29 19:27
 * @Description
 **/
public class DataContent implements Serializable {
    private Integer action; //动作类型
    private ChatMsg chatMsg; //聊天内容
    private String extend; //扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
