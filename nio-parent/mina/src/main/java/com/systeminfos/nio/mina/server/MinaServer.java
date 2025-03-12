package com.systeminfos.nio.mina.server;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;

import java.net.InetSocketAddress;

public class MinaServer {
    public static void main(String[] args) throws Exception {
        // 创建一个非阻塞的 NioSocketAcceptor
        IoAcceptor acceptor = new NioSocketAcceptor();

        // 配置过滤器链
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory())); // 使用文本协议编解码器

        // 设置处理器（用于处理业务逻辑）
        acceptor.setHandler(new IoHandlerAdapter() {
            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                System.out.println("Received message: " + message);
                session.write("Hello, client!");
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                System.out.println("Client connected: " + session.getRemoteAddress());
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
                System.out.println("Client disconnected: " + session.getRemoteAddress());
            }
        });

        // 绑定端口
        acceptor.bind(new InetSocketAddress(9123));
        System.out.println("Server started on port 9123...");
    }
}