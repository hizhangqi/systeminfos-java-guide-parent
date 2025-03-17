package com.systeminfos.nio.netty.websocket2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new HttpServerCodec()); // HTTP编解码器
        ch.pipeline().addLast(new HttpObjectAggregator(65536)); // 聚合 HTTP 消息
        ch.pipeline().addLast(new ChunkedWriteHandler()); // 支持大数据流
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws")); // WebSocket 协议处理器，指定路径
        ch.pipeline().addLast(new WebSocketFrameHandler()); // 自定义的 WebSocket 消息处理器
    }
}