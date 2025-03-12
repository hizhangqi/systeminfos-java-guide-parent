package com.systeminfos.nio.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import java.net.InetSocketAddress;

public class MinaClient {
    public static void main(String[] args) throws Exception {
        // 创建非阻塞的连接器
        NioSocketConnector connector = new NioSocketConnector();

        // 配置过滤器链
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory())); // 使用文本协议编解码器

        // 设置处理器（处理服务器响应）
        connector.setHandler(new IoHandlerAdapter() {
            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                System.out.println("Server said: " + message);
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                System.out.println("Connected to server");
                session.write("Hello, server!");  // 发送消息到服务器
            }
        });

        // 连接到服务器
        ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 9123));
        future.awaitUninterruptibly();  // 等待连接完成
    }
}