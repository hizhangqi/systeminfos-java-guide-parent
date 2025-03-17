package com.systeminfos.nio.netty.websocket2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
public class NettyWebSocketServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    private final int port = 8083; // WebSocket服务端端口

    @PostConstruct
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) {
                             ch.pipeline().addLast(new WebSocketServerInitializer());
                         }
                     });

            ChannelFuture future = bootstrap.bind(port).sync();
            channel = future.channel();
            System.out.println("Netty WebSocket server started on port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    @PreDestroy
    public void stop() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("Netty WebSocket server stopped.");
    }
}