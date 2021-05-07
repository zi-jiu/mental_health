package com.zj.demo.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName WSServerInitializer
 * @Author 字九
 * @Date 2021/3/29 10:48
 * @Description
 **/
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取C/S管道，pipeline
        ChannelPipeline pipeline = channel.pipeline();
        //处理获取的数据
        //编解码器
        pipeline.addLast(new HttpServerCodec());
        //对数据流写提供支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对HttpMessage进行聚合处理
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //设置WebSocket-Server的路由地址
        //WebSocketServerProtocolHandler会处理一些复杂的事情（握手，ping_pong心跳）
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //添加自定义的Hander这里是聊天
        pipeline.addLast(new ChatHander());

    }
}
