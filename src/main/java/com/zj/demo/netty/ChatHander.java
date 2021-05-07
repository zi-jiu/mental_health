package com.zj.demo.netty;



import com.zj.demo.SpringUtil;
import com.zj.demo.enums.MsgActionEnum;
import com.zj.demo.service.UserService;
import com.zj.demo.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChatHander
 * @Author 字九
 * @Date 2021/3/29 11:21
 * @Description 处理消息的Handler，传递消息的载体是frame, WebSocket里的具体实现是TextWebSocketFrame
 **/
public class ChatHander extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //同于记录和管理所有客户端的通道
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //服务器获取客户端的消息
        String content = msg.text();
        System.out.println("服务器接收到的数据"+content);
        //按消息类型处理消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        Channel channel = ctx.channel();
        //分类
        if(action == MsgActionEnum.CONNECT.type) {
            //关联channel和userId
            //获取发送者Id
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId,channel);

            //测试
            for (Channel c:users) {
                System.out.println(c.id().asLongText());
            }
            UserChannelRel.output();

        }else if(action == MsgActionEnum.CHAT.type) {
            //保存聊天内容（加密的）
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgContent = chatMsg.getMsg();
            String senderId = chatMsg.getSenderId();
            String receiverId = chatMsg.getReceiverId();
            //保存到数据库，并且此时未签收
            UserService userService = (UserService) SpringUtil.getBean("UserServiceImpl");
            String msgId = userService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            //保存聊天内容（包括发送接收者id之类的）
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            //发送消息
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if(receiverChannel == null){
                //离线用户


            }else{
                //在ChannelGroup找对应的channel，查看是否存在
                Channel findChannel = users.find(receiverChannel.id());
                //判断对方在不在，能不能发送消息
                if(findChannel!=null){
                    //存在，对方在线，可连接
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContentMsg)
                            )
                    );
                }else{
                    //不存在，离线
                }

            }


        }else if(action == MsgActionEnum.SIGNED.type) {
            //标记消息的签收状态(已签收),修改数据库状态(就是发消息时前端转圈圈的那个)
            UserService userService = (UserService) SpringUtil.getBean("UserServiceImpl");
            //扩展字段在signed类型消息中，代表需要签收的消息Id，以逗号隔开的
            String extend = dataContent.getExtend();
            String[] msgsId = extend.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String s : msgsId) {
                //如果不为空
                if(StringUtils.isNotBlank(s)){
                    msgIdList.add(s);
                }
            }

            System.out.println(msgIdList.toString());
            if(msgIdList!=null && !msgIdList.isEmpty() && msgIdList.size()>0){

                //批量签收
                userService.updateMsgSigned(msgIdList);

            }


        }else if(action == MsgActionEnum.KEEPALIVE.type) {
            //心跳

        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //这步在关闭浏览器时会自动操作
        //clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长Id是:"+ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短Id是:"+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生了异常后关闭链接,并且从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());


    }
}
